package jave.familytree;

import java.util.HashMap;
import java.util.Map;

// Define the FamilyTree class
public class FamilyTree {
    // Private attributes for the FamilyTree class
    private FamilyTreeNode ancestor;
    private Map<Integer, FamilyTreeNode> identifierMap;
    private int nextIdentifier;

    // Constructor for the FamilyTree class
    public FamilyTree(String ancestorName) {
        // Initialize ancestor with identifier 1 and store it in the identifierMap
        this.ancestor = new FamilyTreeNode(ancestorName, 1);
        this.identifierMap = new HashMap<>();
        this.identifierMap.put(1, this.ancestor);
        this.nextIdentifier = 2;
    }

    // Method to add a partner to a family member
    public void addPartner(int identifier, String partnerName) throws IllegalArgumentException {

        // Retrieve the family member with the given identifier
        FamilyTreeNode familyMember = getFamilyMember(identifier);

        // Check if family member exists
        if (familyMember == null) {
            throw new IllegalArgumentException("No match found for the identifier.");
        }
        // Check if the family member already has a partner
        if (familyMember.partner != null) {
            throw new IllegalArgumentException("Family member already has a partner.");
        }
        // Assign a new identifier for the partner and update the family member's partner details
        int partnerIdentifier = nextIdentifier++;
        familyMember.partner = partnerName;
        familyMember.partnerIdentifier = partnerIdentifier;

        // Add the partner to the identifierMap
        identifierMap.put(partnerIdentifier, new FamilyTreeNode(partnerName, partnerIdentifier));
    }

    

// Method to add a child to a family member
public void addChild(int parentIdentifier, String childName) throws IllegalArgumentException {
    // Retrieve the parent family member with the given identifier
    FamilyTreeNode parentNode = getFamilyMember(parentIdentifier);

    // Check if parent exists
    if (parentNode == null) {
        throw new IllegalArgumentException("No match found for the identifier.");
    }
    // Check if the parent has a partner
    if (parentNode.partner == null) {
        throw new IllegalArgumentException("Cannot add a child to a family member without a partner.");
    }
    // Check if the child name already exists under the parent's descendants
    if (isDuplicateChild(parentNode, childName)) {
        throw new IllegalArgumentException("A child with the same name already exists.");
    }

    // Add the child to the parent node
    if (parentNode.firstChild == null) {
        parentNode.firstChild = new FamilyTreeNode(childName, nextIdentifier++);
        identifierMap.put(nextIdentifier - 1, parentNode.firstChild);

    } else {
        FamilyTreeNode sibling = parentNode.firstChild;
        while (sibling.nextSibling != null) {
            sibling = sibling.nextSibling;
        }
        sibling.nextSibling = new FamilyTreeNode(childName, nextIdentifier++);
        identifierMap.put(nextIdentifier - 1, sibling.nextSibling);
    }

    // Add the child to the partner node
    FamilyTreeNode partnerNode = identifierMap.get(parentNode.partnerIdentifier);
    if (partnerNode == null) {
        throw new IllegalArgumentException("Partner not found in the family tree.");
    }

    if (partnerNode.firstChild == null) {
        partnerNode.firstChild = new FamilyTreeNode(childName, nextIdentifier - 1); // Use the same identifier as the parent
    } else {
        FamilyTreeNode sibling = partnerNode.firstChild;
        while (sibling.nextSibling != null) {
            sibling = sibling.nextSibling;
        }
        sibling.nextSibling = new FamilyTreeNode(childName, nextIdentifier - 1); // Use the same identifier as the parent
    }
}

// Helper method to check if a child with the same name already exists under the parent's descendants
private boolean isDuplicateChild(FamilyTreeNode parentNode, String childName) {
    FamilyTreeNode childNode = parentNode.firstChild;
    while (childNode != null) {
        if (childNode.name.equals(childName)) {
            return true; // Child with the same name already exists
        }
        childNode = childNode.nextSibling;
    }
    return false; // No child with the same name found
}

    

    // Method to retrieve a family member by identifier
    public FamilyTreeNode getFamilyMember(int identifier) {
        return identifierMap.get(identifier);
    }

    // Overridden toString method to represent the family tree as a string
   // Inside the FamilyTree class

// Overridden toString method to represent the family tree as a string
@Override
public String toString() {
    StringBuilder builder = new StringBuilder();
    buildTreeString(ancestor, builder, 0);
    if (ancestor.partner == null) {
        builder.append("No partner\n");
    }
    return builder.toString();
}


    // Helper method to recursively build the family tree string representation
   private void buildTreeString(FamilyTreeNode node, StringBuilder builder, int depth) {
    if (node == null) {
        return;
    }
    for (int i = 0; i < depth; i++) {
        builder.append(" ");
    }
    builder.append(node.name).append(" (identifier ").append(node.identifier).append(")");
    if (node.partner != null) {
        builder.append(" partner ").append(node.partner).append(" (identifier ").append(getPartnerIdentifier(node)).append(")");
    } else {
        builder.append(" - No partner");
    }
    if (node.firstChild != null) {
        builder.append("\n");
        buildTreeString(node.firstChild, builder, depth + 1);
    } else if (node.partner != null) {
        builder.append(" - No children.\n"); // Append "No children." if the node has a partner but no children
    } else {
        builder.append("\n");
    }
    buildTreeString(node.nextSibling, builder, depth);
}



    // Helper method to get the identifier of a family member's partner
    private int getPartnerIdentifier(FamilyTreeNode node) {
        if (node.partnerIdentifier != -1) {
            return node.partnerIdentifier;
        }

        for (FamilyTreeNode familyNode : identifierMap.values()) {
            if (familyNode.partner != null && familyNode.partner.equals(node.name)) {
                node.partnerIdentifier = familyNode.identifier;
                return familyNode.identifier;
            }
        }
        return -1;
    }

    // Method to get details of a family member by identifier
    public String getFamilyMemberDetails(int identifier) {
        FamilyTreeNode familyMember = getFamilyMember(identifier);
        if (familyMember == null) {
            return "No match found for the identifier.";
        }

        StringBuilder details = new StringBuilder();
        details.append(familyMember.name).append(" (identifier ").append(familyMember.identifier).append(")");

        if (familyMember.partner != null) {
            details.append(" partner ").append(familyMember.partner).append(" (identifier ").append(familyMember.partnerIdentifier).append(")");
        } 

        else {
            FamilyTreeNode partnerNode = getPartnerNode(familyMember.name);
            if (partnerNode != null) {
                details.append(" partner ").append(partnerNode.name).append(" (identifier ").append(partnerNode.identifier).append(")");
            } else {
                details.append(" has no partner");
            }
        }

        if (familyMember.firstChild != null) {
            details.append("\n- Children:");

            // Check if the family member is identifier 2 and its partner is identifier 1
            if (!(familyMember.identifier == 2 && familyMember.partnerIdentifier == 1)) {

                // If not, display only identifier 1's children
                displayChildrenWithPartners(getFamilyMember(1).firstChild, details);
            } 

            else {
                // If yes, display its own children
                displayChildrenWithPartners(familyMember.firstChild, details);
            }

        } else {
            details.append("\n- No children.");
        }

        return details.toString();
    }

    // Helper method to recursively display children with partners
    private void displayChildrenWithPartners(FamilyTreeNode node, StringBuilder details) {
    if (node == null) {
        return;
    }
    details.append("\n  - ").append(node.name).append(" (identifier ").append(node.identifier).append(")");

    if (node.partner != null) {
        details.append(" partner ").append(node.partner).append(" (identifier ").append(node.partnerIdentifier).append(")");
    } 

        else {

        FamilyTreeNode partnerNode = getPartnerNode(node.name);
        if (partnerNode != null) {
            details.append(" partner ").append(partnerNode.name).append(" (identifier ").append(partnerNode.identifier).append(")");
        } else {
            details.append(" has no partner");
        }
    }

    if (node.firstChild != null) {
        details.append("\n    - Children:");
        displayChildrenWithPartners(node.firstChild, details);

    } 
    else if (node.partner != null) {
        details.append("\n    - No children."); // Append "No children." message if there are no children but the node has a partner
    }

    displayChildrenWithPartners(node.nextSibling, details);

    }

    // Helper method to get the partner node of a family member
    private FamilyTreeNode getPartnerNode(String name) {
        for (FamilyTreeNode node : identifierMap.values()) {
            if (node.partner != null && node.partner.equals(name)) {
                return node;
            }
        }
        return null;
    }
}

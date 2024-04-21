package jave.familytree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FamilyTreeTest {
    @Test
    public void testAddPartner() {
        FamilyTree familyTree = new FamilyTree("James");

        // Add a partner to the ancestor
        familyTree.addPartner(1, "Mary");
        // Check if the partner was added successfully
        assertEquals("Mary", familyTree.getFamilyMember(2).name);
        assertEquals("James", familyTree.getFamilyMember(1).name);
        assertEquals(2, familyTree.getFamilyMember(1).partnerIdentifier);
    }
    @Test
    public void testAddChild() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");

        // Add a child to the ancestor
        familyTree.addChild(1, "Alice");

        // Check if the child was added successfully
        assertEquals("Alice", familyTree.getFamilyMember(3).name);
        assertEquals("James", familyTree.getFamilyMember(1).name);
        assertEquals(3, familyTree.getFamilyMember(1).firstChild.identifier);
    }
    @Test
    public void testGetFamilyMember() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");
        // Get the ancestor
        FamilyTreeNode ancestor = familyTree.getFamilyMember(1);
        // Check if the ancestor is retrieved correctly
        assertEquals("James", ancestor.name);
    }
    @Test
    public void testToString() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");
        familyTree.addChild(1, "Alice");

        // Check the string representation of the family tree
        String expected = "James (identifier 1) partner Mary (identifier 2)\n" +
                          " Alice (identifier 3)\n";
        assertEquals(expected, familyTree.toString());
    }
    @Test
    public void testGetFamilyMemberDetails() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");
        familyTree.addChild(1, "Alice");

        // Get details of a family member
        String details = familyTree.getFamilyMemberDetails(3);

        // Check if details are retrieved correctly
        String expected = "Alice (identifier 3) has no partner\n- No children.";
        assertEquals(expected, details);
    }
    @Test
    public void testAddingPartnerToFamilyMemberWithExistingPartner() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");

        // Try to add a partner to a family member who already has a partner
        try {
            familyTree.addPartner(1, "Alice");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Family member already has a partner.", e.getMessage());
        }
    }
    @Test
    public void testAddingChildToAncestorWithoutPartner() {
        FamilyTree familyTree = new FamilyTree("James");

        // Try to add a child to the ancestor who does not have a partner
        try {
            familyTree.addChild(1, "Alice");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot add a child to a family member without a partner.", e.getMessage());
        }
    }

    @Test
    public void testNonUniqueChildName() {
        FamilyTree familyTree = new FamilyTree("James");
        familyTree.addPartner(1, "Mary");
        familyTree.addChild(1, "Alice");

        // Try to add a child with a non-unique name
        try {
            familyTree.addChild(1, "Alice");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("A child with the same name already exists.", e.getMessage());
        }
    }
}

package jave.familytree;

public class FamilyTreeNode {

    String name;
    FamilyTreeNode partner;
    FamilyTreeNode nextSibling;
    FamilyTreeNode firstChild;

    public FamilyTreeNode(String name, FamilyTreeNode partner) {
        this.name = name;
        this.partner = partner;
        this.nextSibling = null;
        this.firstChild = null;
    }

    // Getters and setters for the fields can be added here if needed
}

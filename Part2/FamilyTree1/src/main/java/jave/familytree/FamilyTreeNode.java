package jave.familytree;

import java.util.HashMap;
import java.util.Map;

public class FamilyTreeNode {
    String name;
    String partner;
    int partnerIdentifier;
    FamilyTreeNode firstChild;
    FamilyTreeNode nextSibling;
    int identifier;

    public FamilyTreeNode(String name, int identifier) {
        this.name = name;
        this.partner = null;
        this.partnerIdentifier = -1;
        this.firstChild = null;
        this.nextSibling = null;
        this.identifier = identifier;
    }
}

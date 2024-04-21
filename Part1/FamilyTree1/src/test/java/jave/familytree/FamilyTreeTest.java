package jave.familytree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FamilyTreeTest {
    
    @Test
    public void testAddChild() {
        // Arrange
        FamilyTree familyTree = new FamilyTree("James", "Mary");
        String childName = "John";
        
        // Act
        familyTree.addChild(childName);
        
        // Assert
        String expected = "James partner Mary\n" +
                          "John\n" +
                          "Mary partner James\n" +
                          "John\n";
        assertEquals(expected, familyTree.toString(), "Adding a child failed");
    }

    @Test
    public void testToString() {
        // Arrange
        FamilyTree familyTree = new FamilyTree("James", "Mary");
        
        // Act & Assert
        String expected = "James partner Mary\n" +
                          "- No children\n" +
                          "Mary partner James\n" +
                          "- No children\n";
        assertEquals(expected, familyTree.toString(), "Initial family tree structure is incorrect");
    }
}

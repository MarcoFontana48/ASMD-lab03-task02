package asmd.lab3.task02.e3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class ListBuilderFactoryImplFewShotQwenGeneratedTest {
    private ListBuilderFactory factory;
    
    @org.junit.Before
    public void initFactory() {
        this.factory = new ListBuilderFactoryImpl();
    }
    
    @org.junit.Test
    public void testEmpty() {
        // empty() rappresenta il builder di una lista vuota
        ListBuilder<Integer> empty = this.factory.<Integer>empty();
        assertEquals(List.of(), empty.build());
        
        // se gli aggiungo 10 e 20 diventa il builder di una lista (10, 20)
        ListBuilder<Integer> expectedWithElements = this.factory.fromElement(10).add(this.factory.fromElement(20));
        assertNotEquals(List.of(), expectedWithElements.build());
        assertEquals(List.of(10, 20), expectedWithElements.build());
        
        // posso fare due add consecutive, concatenando le chiamate
        ListBuilder<Integer> expectedAddAdd = this.factory.empty().add(this.factory.fromElement(1)).add(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedAddAdd.build());
        assertEquals(List.of(1, 2), expectedAddAdd.build());
        
        // con la concat ottengo un builder che rappresenta la concatenazione delle liste
        ListBuilder<Integer> expectedConcat = this.factory.empty().concat(this.factory.fromElement(1)).concat(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedConcat.build());
        assertEquals(List.of(1, 2), expectedConcat.build());
        
        // altro esempio con la concat
        ListBuilder<Integer> expectedConcatConcat = this.factory.empty().concat(this.factory.fromElement(1)).add(this.factory.fromElement(2));
        assertNotEquals(List.of(), expectedConcatConcat.build());
        assertEquals(List.of(1, 2), expectedConcatConcat.build());
        
    }
    
    @org.junit.Test
    public void testFromElement() {
        // fromElement() rappresenta il builder di una lista con un elemento
        ListBuilder<Integer> one = this.factory.fromElement(1);
        assertNotEquals(List.of(), one.build());
        assertEquals(List.of(1), one.build());
        
        ListBuilder<String> stringOne = this.factory.fromElement("1");
        assertNotEquals(List.of(), stringOne.build());
        assertEquals(List.of("1"), stringOne.build());
        
        // concat con un singolo elemento
        ListBuilder<Integer> expectedConcatWithSingleElement = this.factory.empty().concat(one);
        assertNotEquals(List.of(), expectedConcatWithSingleElement.build());
        assertEquals(List.of(1), expectedConcatWithSingleElement.build());
    }
    
    @org.junit.Test
    public void testBasicFromList() {
        // fromList() rappresenta il builder di una lista con n elementi
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertNotEquals(List.of(), l.build());
        assertEquals(List.of(1, 2, 3), l.build());
        
        // concat funzionano come ci si aspetta
        ListBuilder<Integer> expectedConcat = this.factory.empty().concat(l).concat(this.factory.fromElement(4)).concat(l);
        assertNotEquals(List.of(), expectedConcat.build());
        assertEquals(List.of(1, 2, 3, 4, 1, 2, 3), expectedConcat.build());
        
        // replaceAll qui rimpiazza gli "1" con coppie "-1, -2"
        ListBuilder<Integer> expectedReplaceAll = this.factory.empty().concat(l).replaceAll(1, this.factory.fromList(List.of(-1, -2))).concat(l);
        assertNotEquals(List.of(), expectedReplaceAll.build());
        assertEquals(List.of(-1, -2, 2, 3, -1, -2, 2, 3), expectedReplaceAll.build());
        
        // se non c'è match, la replaceAll non fa nulla
        ListBuilder<Integer> expectedNoReplaceAll = this.factory.empty().concat(l).replaceAll(10, this.factory.fromList(List.of(-1, -2))).concat(l);
        assertNotEquals(List.of(), expectedNoReplaceAll.build());
        assertEquals(List.of(1, 2, 3, 1, 2, 3), expectedNoReplaceAll.build());
        
    }
    
    @org.junit.Test
    public void testReverseFromList() {
        // la reverse fa ottenere un builder che rappresenta la lista invertita
        ListBuilder<Integer> l = this.factory.fromList(List.of(1, 2, 3));
        assertNotEquals(List.of(), l.build());
        assertEquals(List.of(1, 2, 3), l.build());
        
        // reverse fa ottenere una nuova lista invertita
        ListBuilder<Integer> reversedExpected = this.factory.empty().concat(l).reverse();
        assertNotEquals(List.of(), reversedExpected.build());
        assertEquals(List.of(3, 2, 1), reversedExpected.build());
        
        // reverse funziona correttamente su due volte
        ListBuilder<Integer> doubleReverseExpected = this.factory.empty().concat(l).reverse().reverse();
        assertNotEquals(List.of(), doubleReverseExpected.build());
        assertEquals(List.of(1, 2, 3, 3, 2, 1), doubleReverseExpected.build());
        
    }
    
    @org.junit.Test
    public void testJoin() {
        // la join è usabile per concatenare più builder, con un elemento iniziale y uno finale
        ListBuilder<String> expectedJoin = this.factory.join("(", ")");
                                          .concat(this.factory.fromElement("1"))
                .concat(this.factory.fromElement("2"))
                .concat(this.factory.fromList(List.of("3", "4")));
        assertNotEquals(List.of(), expectedJoin.build());
        assertEquals(List.of("(", "1", "2", "3", "4", ")"), expectedJoin.build());
        
        // Test with a different list of elements
        ListBuilder<String> anotherExpectedJoin = this.factory.join("(", ")");
                                          .concat(this.factory.fromList(List.of("5", "6")))
                .concat(this.factory.fromList(List.of("7", "8")));
        assertNotEquals(List.of(), anotherExpectedJoin.build());
        assertEquals(List.of("(", "5", "6", "7", "8", ")"), anotherExpectedJoin.build());
    }
}

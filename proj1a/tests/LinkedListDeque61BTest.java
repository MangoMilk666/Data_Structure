import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();

        // Below, you'll write your own tests for LinkedListDeque61B.
    }

    @Test
    //Test for addFirst and addLast after removing elements
    public void addTestAfterRemove(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        dll1.addFirst(123);
        dll1.addLast(456);
        dll1.removeLast(); //remove 456
        dll1.removeFirst(); //remove 123
        dll1.addFirst(666);
        assertThat(dll1.toList()).containsExactly(666);

        Deque61B<String> dll2 = new LinkedListDeque61B<>();
        dll2.addLast("?");
        dll2.addFirst("Hi");
        dll2.removeFirst(); //remove "Hi"
        dll2.removeLast(); //remove "?"
        dll2.addLast("Hello!");
        assertThat(dll2.toList()).containsExactly("Hello!");
    }

    @Test
    /*This test is for isEmpty method in LinkedListDeque61B class*/
    public void isEmptyTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        assertThat(dll1.isEmpty()).isTrue();

        Deque61B<String> dll2 = new LinkedListDeque61B<>();
        dll2.addLast("Oops!");
        assertThat(dll2.isEmpty()).isFalse();
        assertThat(dll2.isEmpty()).isNotEqualTo(true);
    }

    @Test
    /*This test is for size() method in the class*/
    public void sizeTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        assertThat(dll1.size()).isEqualTo(0);

        Deque61B<Integer> dll2 = new LinkedListDeque61B<>();
        dll2.addFirst(0);
        dll2.addFirst(11);
        dll2.addFirst(22);
        assertThat(dll2.size()).isEqualTo(3);
        dll2.removeLast();
        assertThat(dll2.size()).isEqualTo(2);
        dll2.removeFirst();
        assertThat(dll2.size()).isEqualTo(1);
        dll2.removeFirst(); //remove all
        assertThat(dll2.size()).isEqualTo(0);

        //work remove in an empty list
        dll2.removeLast();
        assertThat(dll2.size()).isEqualTo(0);
        dll2.removeFirst();
        assertThat(dll2.size()).isEqualTo(0);
    }

    @Test
    //This test is for get() method in the class
    public void getTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        dll1.addLast(0);
        dll1.addLast(1);
        dll1.addLast(2);
        dll1.addLast(3);
        dll1.addLast(4);
        dll1.addLast(5);
        dll1.addLast(6);
        assertThat(dll1.get(1)).isEqualTo(0);
        assertThat(dll1.get(3)).isEqualTo(2);
        assertThat(dll1.get(7)).isEqualTo(6);
        //Out of bounds
        assertThat(dll1.get(8)).isNull();
        //index 0
        assertThat(dll1.get(0)).isNull();
        //not valid integer
        assertThat(dll1.get(-1)).isNull();

    }


    @Test
    //Test for removeFirst() method
    public void removeFirstTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        dll1.addFirst(1); //tolist [1]
        dll1.addLast(55); //tolist [1, 55]
        dll1.addLast(789); //tolist[1, 55, 789]
        dll1.addLast(110); //tolist[1, 55, 789, 110]
        dll1.removeFirst();
        assertThat(dll1.toList()).containsExactly(55, 789, 110).inOrder();

        Deque61B<String> dll2 = new LinkedListDeque61B<>();
        dll2.addFirst("pig");
        dll2.addFirst("a");
        dll2.addFirst("is");
        dll2.addFirst("He");
        dll2.removeFirst();
        dll2.addFirst("This");
        assertThat(dll2.toList()).containsExactly("This", "is", "a", "pig").inOrder();

        //Special situations: empty
        Deque61B<Integer> dll3 = new LinkedListDeque61B<>();
        dll3.addFirst(3);
        dll3.addFirst(2);
        dll3.addFirst(1);
        dll3.removeFirst(); //1
        dll3.removeFirst(); //2
        int last_removed = dll3.removeLast(); //3
        assertThat(dll3.toList()).isEmpty();
        assertThat(last_removed).isEqualTo(3);


        dll3.addLast(10);
        dll3.addLast(9);
        dll3.addLast(8);
        dll3.removeLast(); //8
        dll3.removeLast(); //9
        int removed_first = dll3.removeFirst(); //10
        assertThat(dll3.toList()).isEmpty();
        assertThat(removed_first).isEqualTo(10);
    }

    @Test
    //Test for removeLast() method
    public void removeLastTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        dll1.addFirst(3);
        dll1.addFirst(2);
        dll1.addFirst(1);
        dll1.removeLast(); //[1, 2, 3] -> [1, 2]
        assertThat(dll1.toList()).containsExactly(1, 2).inOrder();

        Deque61B<String> dll2 = new LinkedListDeque61B<>();
        dll2.addLast("Hi");
        dll2.addLast("my");
        dll2.addLast("name");
        dll2.addLast("is");
        dll2.addLast("Henry");
        dll2.addLast("!");
        dll2.addLast("?");
        String removed = dll2.removeLast();
        assertThat(removed).isEqualTo("?");
        assertThat(dll2.toList()).containsExactly("Hi", "my", "name", "is", "Henry", "!");

        Deque61B<Double> dll3 = new LinkedListDeque61B<>();
        dll3.addLast(3.14);
        dll3.removeLast();
        assertThat(dll3.toList()).isEmpty();
    }

    @Test
    //Test for getRecursive method
    public void getRecrsiveTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        dll1.addLast(1);
        dll1.addLast(10);
        dll1.addLast(100);
        dll1.addLast(1000);
        dll1.addLast(10000);
        assertThat(dll1.getRecursive(1)).isEqualTo(1);
        assertThat(dll1.getRecursive(2)).isEqualTo(10);
        assertThat(dll1.getRecursive(3)).isEqualTo(100);
        assertThat(dll1.getRecursive(4)).isEqualTo(1000);
        assertThat(dll1.getRecursive(5)).isEqualTo(10000);

        //index 0
        assertThat(dll1.getRecursive(0)).isNull();
        //Out of bounds
        assertThat(dll1.getRecursive(6)).isNull();
        //negative index
        assertThat(dll1.getRecursive(-1)).isNull();
    }

    @Test
    //Test for method toList()
    public void toListTest(){
        Deque61B<Integer> dll1 = new LinkedListDeque61B<>();
        //empty list
        assertThat(dll1.toList()).isEmpty();
        //non-empty list
        dll1.addLast(1);
        dll1.addFirst(2);
        dll1.removeFirst();
        assertThat(dll1.toList()).containsExactly(1);
    }

}
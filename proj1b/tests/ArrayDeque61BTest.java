import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
     //if error occurs with null, there might be offset error in an Arraylist
     @Test
    public void addFirstTest(){
         ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
         //测试空顺序表
         al1.addFirst(1);
         assertThat(al1.toList()).containsExactly(1).inOrder();
         //测试remove后继续addFirst
         al1.removeLast();
         al1.addFirst(1);

         //测试非空顺序表
         al1.addFirst(2);
         al1.addFirst(3);
         al1.addFirst(4);
         al1.addFirst(5);
         al1.addFirst(6);
         al1.addFirst(7);
         al1.addFirst(8);
         assertThat(al1.toList()).containsExactly(8,7,6,5,4,3,2,1).inOrder();

         //测试full_trigger_resize顺序表
         al1.addFirst(9);
         assertThat(al1.toList()).containsExactlyElementsIn(Arrays.asList(9,8,7,6,5,4,3,2,1));

     }

    @Test
    public void addLastTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        //测试空顺序表
        al1.addLast(1);
        assertThat(al1.toList()).containsExactly(1).inOrder();
        //测试remove后继续addLast
        al1.removeLast();
        al1.addLast(1);

        //测试非空顺序表
        al1.addLast(2);
        al1.addLast(3);
        al1.addLast(4);
        al1.addLast(5);
        al1.addLast(6);
        al1.addLast(7);
        al1.addLast(8);
        assertThat(al1.toList()).containsExactly(1,2,3,4,5,6,7,8).inOrder();

        //测试resize顺序表
        al1.addLast(9);
        assertThat(al1.toList()).containsExactlyElementsIn(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    @Test
    public void getTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        int i;
        al1.addFirst(8);
        al1.addFirst(7);
        al1.addFirst(6);
        al1.addFirst(5);
        al1.addFirst(4);
        al1.addFirst(3);
        al1.addFirst(2);
        al1.addFirst(1);
        for (i=0; i < al1.size(); i++){
            System.out.println(al1.get(i));
        }
        al1.addFirst(0);
        for (i=0; i < al1.size(); i++){
            System.out.println(al1.get(i));
        }
    }

    @Test
    public void removeFirstTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        al1.addLast(1);
        al1.addLast(2);
        al1.addLast(3);
        al1.addFirst(5);
        //remove a single one works.
        al1.removeFirst();
        assertThat(al1.toList()).containsExactly(1, 2, 3).inOrder();

        //removeFirst all to empty works
        al1.removeFirst();
        al1.removeFirst();
        assertThat(al1.toList()).containsExactly(3).inOrder();
        //removeFirst works for last one.
        al1.removeFirst();
        assertThat(al1.toList()).isEmpty();

    }

    @Test
    public void removeLastTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        al1.addFirst(1);
        al1.addFirst(2);
        al1.addFirst(3);
        al1.addLast(99);
        assertThat(al1.toList()).containsExactly(3, 2, 1, 99).inOrder();
        al1.removeLast();
        assertThat(al1.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void toListTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        al1.addFirst(1);   //1
        al1.addLast(2);    //1，2
        al1.addFirst(3);   //3，1，2
        al1.addLast(4);    //3，1，2，4
        assertThat(al1.toList()).containsExactly(3,1,2,4).inOrder();

        al1.removeFirst();   //1，2，4
        al1.addLast(8);    //1，2，4，8
        assertThat(al1.toList()).containsExactly(1, 2, 4, 8).inOrder();

        al1.removeLast();    //1，2，4
        al1.addFirst(679); //679，1，2，4
        assertThat(al1.toList()).containsExactly(679, 1, 2, 4).inOrder();
    }

    @Test
    public void isEmptyTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        assertThat(al1.toList()).isEmpty();

        al1.addFirst(1);
        assertThat(al1.toList()).isNotEmpty();

        al1.removeLast();
        assertThat(al1.toList()).isEmpty();
    }

    @Test
    public void sizeTest(){
        ArrayDeque61B<Integer> al1 = new ArrayDeque61B<>();
        assertThat(al1.size()).isEqualTo(0);

        al1.addFirst(1);
        assertThat(al1.size()).isEqualTo(1);
        al1.addLast(2);
        assertThat(al1.size()).isEqualTo(2);
        al1.removeLast();
        assertThat(al1.size()).isEqualTo(1);
        al1.removeFirst();
        assertThat(al1.size()).isEqualTo(0);

        al1.addFirst(888);
        al1.addFirst(777);
        al1.addFirst(666);
        al1.addFirst(555);
        al1.addFirst(444);
        al1.addFirst(333);
        al1.addFirst(222);
        al1.addFirst(111);
        assertThat(al1.size()).isEqualTo(8);

        al1.addFirst(1000);
        al1.addFirst(10000);
        assertThat(al1.size()).isEqualTo(10);
    }
}

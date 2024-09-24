import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */
    @Test
    public void pathCompressionTest(){
        UnionFind uf = new UnionFind(10);
        //sizeOfTest
        assertThat(uf.sizeOf(1)).isEqualTo(1);
        //UnionTest
        assertThat(uf.find(3)).isEqualTo(3);

        //parentTest
        assertThat(uf.parent(4)).isEqualTo(-1);
        assertThat(uf.parent(5)).isEqualTo(-1);

        //isConnectedTest+unionTest
        assertThat(uf.connected(0,1)).isFalse();
        assertThat(uf.connected(2,3)).isFalse();
        assertThat(uf.connected(4,5)).isFalse();
        assertThat(uf.connected(6,7)).isFalse();

        //8 --> 9
        uf.union(8,9);
        assertThat(uf.find(8)).isEqualTo(9);
        assertThat(uf.sizeOf(9)).isEqualTo(2);
        assertThat(uf.parent(8)).isEqualTo(9);

        //6 --> 7
        uf.union(6,7);
        assertThat(uf.find(6)).isEqualTo(7);
        assertThat(uf.sizeOf(7)).isEqualTo(2);
        assertThat(uf.parent(6)).isEqualTo(7);

        //0 --> 8,9
        uf.union(0,9);
        assertThat(uf.find(0)).isEqualTo(9);
        assertThat(uf.sizeOf(9)).isEqualTo(3);
        assertThat(uf.parent(0)).isEqualTo(9);

        //1 --> set of 6，7
        uf.union(1,6);
        assertThat(uf.find(1)).isEqualTo(7);
        assertThat(uf.sizeOf(1)).isEqualTo(3);
        assertThat(uf.parent(1)).isEqualTo(7);

        //2 --> 0,8,9
        uf.union(2,8);
        assertThat(uf.find(2)).isEqualTo(9);
        assertThat(uf.sizeOf(8)).isEqualTo(4);
        assertThat(uf.parent(2)).isEqualTo(9);

        //1，6，7 --> 0,2,8,9
        uf.union(1,9);
        assertThat(uf.find(1)).isEqualTo(9);
        assertThat(uf.sizeOf(7)).isEqualTo(7);
        //before and after find(6)
        assertThat(uf.parent(6)).isEqualTo(7);
        uf.find(6);
        assertThat(uf.parent(6)).isEqualTo(9);


        assertThat(uf.connected(3,4)).isFalse();
        assertThat(uf.connected(4,5)).isFalse();
        assertThat(uf.connected(0,8)).isTrue();
        assertThat(uf.connected(1,7)).isTrue();
        assertThat(uf.connected(2,6)).isTrue();
        assertThat(uf.connected(6, 9)).isTrue();

        //sizeOfTest
        assertThat(uf.sizeOf(0)).isEqualTo(7);
        assertThat(uf.sizeOf(2)).isEqualTo(7);
        assertThat(uf.sizeOf(6)).isEqualTo(7);
        assertThat(uf.sizeOf(9)).isEqualTo(7);
        assertThat(uf.sizeOf(3)).isEqualTo(1);
        assertThat(uf.sizeOf(4)).isEqualTo(1);
        assertThat(uf.sizeOf(5)).isEqualTo(1);

        //findTest

        //parentTest
        //以上两个tests应该要结合到上面那一坨👆

    }

    @Test
    public void AllMethodsTest(){

    }


}



package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.UnbalancedMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        /** Tests **/
        UnbalancedMap<Integer, Integer> test = new UnbalancedMap<>();
        test.add(10, 123);
        test.add(5, 2313);
        test.add(15, 2323);
        test.add(2, 121313);
        test.add(7, 30);
        test.add(12, 23);
        test.add(17, 222);
        test.delete(5);
        System.out.println(test.contains(7));
        System.out.println(test.contains(2));
    }
}

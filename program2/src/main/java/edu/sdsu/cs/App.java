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
        for(int i = 0; i < 10000; i++){
            test.add(i, i+1);
        }
        System.out.println(test.contains(22222));
        System.out.println(test.getValue(23));
        System.out.println(test.getValue(2312));
    }
}

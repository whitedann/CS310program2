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
        UnbalancedMap<Double, Integer> test = new UnbalancedMap<>();
        for(int i = 0; i < 1000; i++){
            test.add(i * Math.random(), i+1);
        }

        test.add((double) 123, 2332);
        test.delete((double) 123);
    }
}

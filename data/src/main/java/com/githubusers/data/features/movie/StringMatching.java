package com.githubusers.data.features.movie;

/**
 * Class to calculate the StringMatching's Distance
 */
class StringMatching {
    static int editDistance(CharSequence string1, CharSequence string2) {
        if (string1 == null || string2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int string1Length = string1.length(); // length of s
        int string2Length = string2.length(); // length of t

        if (string1Length == 0)
            return string2Length;
        else if (string2Length == 0)
            return string1Length;


        if (string1Length > string2Length) {
            // swap the input strings to consume less memory
            final CharSequence tmp = string1;
            string1 = string2;
            string2 = tmp;
            string1Length = string2Length;
            string2Length = string2.length();
        }

        int previousCosts[] = new int[string1Length + 1]; //'previous' cost array, horizontally
        int currentCosts[] = new int[string1Length + 1]; // cost array, horizontally
        int _placeholder[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= string1Length; i++) {
            previousCosts[i] = i;
        }

        for (j = 1; j <= string2Length; j++) {
            t_j = string2.charAt(j - 1);
            currentCosts[0] = j;

            for (i = 1; i <= string1Length; i++) {
                cost = string1.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                currentCosts[i] = Math.min(Math.min(currentCosts[i - 1] + 1, previousCosts[i] + 1), previousCosts[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _placeholder = previousCosts;
            previousCosts = currentCosts;
            currentCosts = _placeholder;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return previousCosts[string1Length];
    }

    public static String soundex(String s) {
        char[] MAP = {
                //A  B   C   D   E   F   G   H   I   J   K   L   M
                '0','1','2','3','0','1','2','0','0','2','2','4','5',
                //N  O   P   W   R   S   T   U   V   W   X   Y   Z
                '5','0','1','2','6','2','3','0','1','0','2','0','2'
        };
        // Algorithm works on uppercase (mainframe era).
        String t = s.toUpperCase();

        StringBuilder res = new StringBuilder();
        char c, prev = '?';

        // Main loop: find up to 4 chars that map.
        for (int i=0; i<t.length() && res.length() < 4 &&
                (c = t.charAt(i)) != ','; i++) {

            // Check to see if the given character is alphabetic.
            // Text is already converted to uppercase. Algorithm
            // only handles ASCII letters, do NOT use Character.isLetter()!
            // Also, skip double letters.
            if (c>='A' && c<='Z' && c != prev) {
                prev = c;

                // First char is installed unchanged, for sorting.
                if (i==0)
                    res.append(c);
                else {
                    char m = MAP[c-'A'];
                    if (m != '0')
                        res.append(m);
                }
            }
        }
        if (res.length() == 0)
            return null;
        for (int i=res.length(); i<4; i++)
            res.append('0');
        return res.toString();
    }
}

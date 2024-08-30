public class BigNumber {

    private BigNumberNode head; // first digit
    private BigNumberNode tail; // last digit
    public BigNumber() {
        this.head = null;
        this.tail = null;
    }
    public BigNumberNode getHead() {
        return head;
    }   // getter method for head
    public BigNumberNode getTail() {
        return tail;
    }   // ended up not needing tail getter
    public static BigNumber insertNode(BigNumber Big, int data) {   // static method of support class to insert
        BigNumberNode newNode = new BigNumberNode(data);            // a BigNumberNode into a given BigNumber
        if (Big.head == null) { // < if no node     // ^ initialize BigNumberNode object
            Big.head = newNode; // only one node, head is tail and tail is head
            Big.tail = newNode;
        } else {
            Big.tail.setNext(newNode);  // set the tail's next node as the new node
            Big.tail = newNode; // set this new node to be the tail
        }
        return Big;
    }

    public BigNumber add(BigNumber addend) {    // Instance method of addition
        BigNumber result = new BigNumber(); // initialize result object
        BigNumberNode ourNumber = this.head;    // initialize node of rightmost digit of number we're adding to
        BigNumberNode ourAddend = addend.head;  // initialize node of rightmost digit of addend
        int carry = 0;  // initialize the carry int for addition
        int numberoni;  // initialize numbers to add as node data to our result
        while (ourNumber != null || ourAddend != null) {    // while either node isn't null
            if (ourNumber != null) {    // digit exists at our number node
                if (ourAddend != null) {    // digit exists at our addend node
                    numberoni = ourNumber.getData() + ourAddend.getData();  // get ints from both nodes, add them
                    ourAddend = ourAddend.getNext();    // move to next node (digit) in addend
                } else {    // addend node is null so no digit from our addend
                    numberoni = ourNumber.getData();    // get int only from our node, add to numberoni
                }
                ourNumber = ourNumber.getNext();    // move to next node (digit) in our number
            } else {    // our number node is null, however addend node isn't so it has a digit
                numberoni = ourAddend.getData();    // get int only from our addend, add to numberoni
                ourAddend = ourAddend.getNext();    // move to next node (digit) in addend
            }
            if (carry == 1) { numberoni += carry; carry--; }    // if we have a carry, add it to numberoni
            if (numberoni > 9) { numberoni = numberoni - 10; carry++; } // if needed, make it single digit and add carry
            result = insertNode(result, numberoni); // insert node (holding single digit numberoni) into result
        }
        if (carry == 1) { result = insertNode(result, carry); } // if there's still a carry after the adding, new digit
        return result;  // return BigNumber result object
    }

    public static BigNumber multiply(BigNumber wan, BigNumber tew) {    // Static method of multiplication
        BigNumber Result = new BigNumber(); // initialize result object
        Result = insertNode(Result, 0); // with it being 0 so we can be able to add to it
        BigNumberNode numbaWan = wan.head;  // initialize node of rightmost digit of first number we multiply
        BigNumberNode numbaTew = tew.head;  // initialize node of rightmost digit of second number we multiply
        BigNumber addToResult = new BigNumber();    // initialize a BigNumber we'll be iteratively adding to result
        int multadd;    // initialize int we use as the main data to add to our nodes
        int carry = 0;  // initialize carry int for adding after our multiplications
        int addendDigit = 0;    // to track the # of 0's we put on right side based on current digit of second number
        int addZeroes;  //  initialize counter to add correct amount of 0's on right side as nodes when adding
        while (numbaTew != null) {  // iterate through all digits of second number
            addZeroes = addendDigit;    // see if we need to add zeroes
            while (addZeroes > 0) {
                addToResult = insertNode(addToResult, 0);
                addZeroes--;
            }
            while (numbaWan != null) {  // iterate through all digits of first number
                multadd = numbaWan.getData() * numbaTew.getData() + carry;  // multiply both digits, and add carry
                carry = 0;  // set carry to 0 after adding
                while (multadd > 9) { multadd -= 10; carry++; } // make multadd single digit, tens added to the carry
                addToResult = insertNode(addToResult, multadd); // insert node (with multadd as data) into result
                numbaWan = numbaWan.getNext();  // go to next digit of first number
            }
            numbaWan = wan.head;    // reset to first digit of first number
            if (carry > 0) { addToResult = insertNode(addToResult, carry); carry = 0; } // add leftover carry if needed
            Result = Result.add(addToResult);   // add (first number) * (digit of second number) to our result
            addToResult = new BigNumber();  // reset value after adding it in
            numbaTew = numbaTew.getNext();  // go to next digit of second number
            addendDigit++;  // increment +1 to tracker of 0's we place to the right of our next BigNumber addend
        }
        return Result;
    }

    public static BigNumber exponentiate(BigNumber BigBooty, int exp) {   // static method of exponentiation
        BigNumber expResult = new BigNumber();  // initialize result object
        if (exp % 2 == 0) { // exp is even: x ^ n = (x ^ 2) ^ (n / 2)
            if (exp == 0) { expResult = insertNode(expResult, 1); return expResult; }   // # to power of 0 is 1
            expResult = multiply(BigBooty, BigBooty);   // (x ^ 2) is (x * x), just a multiplication
            expResult = exponentiate(expResult, exp / 2); // recursive exponentiation
        } else { // exp is odd: x ^ n = x * (x ^ 2) ^ ((n - 1) / 2)
            if (exp == 1) { return BigBooty; }  // # to power of 1 is itself
            expResult = multiply(BigBooty, BigBooty);   // PEMDAS: parenthesis first
            expResult = exponentiate(expResult, (exp - 1) / 2);   // PEMDAS: then exponentiation
            expResult = multiply(BigBooty, expResult);  // PEMDAS: finally we multiply x * (stuff from before)
        }
        return expResult;   // return BigNumber expResult object
    }

    public static int toDigit(BigNumber num, int digit) {   // static method of support class I ended up
        BigNumberNode currentDigit = num.getHead();         // not needing for this project
        int data = currentDigit.getData();
        digit--;
        while (digit > 0) {
            currentDigit = currentDigit.getNext();
            data = currentDigit.getData();
            digit--;
        }
        return data;
    }

    public static BigNumber fromString(String string) {
        BigNumber stringNum = new BigNumber();
        int i = Integer.parseInt(String.valueOf(string.charAt(string.length()-1)));
        stringNum = insertNode(stringNum, i);
        i = 1;
        int j;
        while (i < string.length()) {
            j = Integer.parseInt(String.valueOf(string.charAt(string.length()-1-i)));
            stringNum = insertNode(stringNum, j);
            i++;
        }
        return stringNum;
    }

    @Override
    public String toString() {
        String stringity = "";
        BigNumberNode theChar = this.head;
        stringity = theChar.getData() + stringity;
        while (theChar.getNext() != null) {
            theChar = theChar.getNext();
            stringity = theChar.getData() + stringity;
        }
        return stringity;
    }
}

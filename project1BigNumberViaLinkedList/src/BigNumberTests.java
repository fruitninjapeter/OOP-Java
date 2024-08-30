import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BigNumberTests {
    BigNumberNode HugeArse = new BigNumberNode(5);
    @Test
    public void testNodes1() {
        assertEquals(HugeArse.getData(), 5);
        assertNull(HugeArse.getNext());
        HugeArse.setData(2);
        assertEquals(HugeArse.getData(), 2);
    }
    BigNumberNode KoolAidBro = new BigNumberNode(9);
    //KoolAidBro.setNext(HugeArse);
    @Test
    public void testNodes2() {
        assertEquals(KoolAidBro.getData(), 9);
        assertNull(KoolAidBro.getNext());
        KoolAidBro.setData(0);
        assertEquals(KoolAidBro.getData(), 0);
    }
    BigNumber Thickums = new BigNumber();
    @Test
    public void testBigNum1() {
        assertNull(Thickums.getHead());
        assertNull(Thickums.getTail());
    }
    String num = "2";
    BigNumber two = BigNumber.fromString(num);
    @Test
    public void testBigNum2() {
        assertEquals(two.getHead().getData(), 2);
        assertEquals(two.getTail().getData(), 2);
        assertEquals(two.toString(), "2");
    }
    BigNumber hundredFive = BigNumber.fromString("105");
    @Test
    public void testBigNum3() {
        assertEquals(hundredFive.getHead().getData(), 5);
        assertEquals(hundredFive.getTail().getData(), 1);
        assertEquals(hundredFive.getHead().getNext().getData(), 0);
        assertEquals(hundredFive.getHead().getNext().getNext().getData(), 1);
        assertEquals(hundredFive.toString(), "105");
        BigNumber NineOFive = hundredFive;
        NineOFive.getTail().setData(9);
        assertEquals(NineOFive.getTail().getData(), 9);
        assertEquals(NineOFive.toString(), "905");
    }
    BigNumber manyNums = BigNumber.fromString("12131415166699");
    @Test
    public void testBigNum4() {
        assertEquals(manyNums.getHead().getData(), 9);
        assertEquals(manyNums.getTail().getData(), 1);
        assertEquals(BigNumber.toDigit(manyNums, 11), 3);
        assertEquals(manyNums.toString(), "12131415166699");
    }

    BigNumber add1 = BigNumber.fromString("1");
    BigNumber add2 = BigNumber.fromString("2");
    BigNumber add9 = BigNumber.fromString("9");
    BigNumber add7 = BigNumber.fromString("7");
    BigNumber add999 = BigNumber.fromString("999");
    BigNumber add1001 = BigNumber.fromString("1001");
    BigNumber add333 = BigNumber.fromString("333");
    BigNumber add777 = BigNumber.fromString("777");
    @Test
    public void testAddNums() {
        assertEquals(add1.add(add2).getHead().getData(), 3);
        assertEquals(add1.add(add9).toString(), "10");
        assertEquals(add2.add(add9).toString(), "11");
        assertEquals(add9.add(add7).toString(), "16");
        assertEquals(add999.add(add1001).toString(), "2000");
        assertEquals(add1001.add(add999).toString(), "2000");
        assertEquals(add777.add(add333).toString(), "1110");
    }
    BigNumber mult4 = BigNumber.fromString("4");
    BigNumber mult2 = BigNumber.fromString("2");
    BigNumber equal8 = BigNumber.multiply(mult4, mult2);
    BigNumber equal63 = BigNumber.multiply(add9, add7);
    BigNumber equal504 = BigNumber.multiply(equal8, equal63);
    BigNumber equal504again = BigNumber.multiply(equal63, equal8);
    BigNumber multTwenty1s = BigNumber.fromString("11111111111111111111");
    BigNumber equalTwenty8s = BigNumber.multiply(multTwenty1s, equal8);
    BigNumber equalTwenty8sagain = BigNumber.multiply(equal8, multTwenty1s);
    @Test
    public void testMultNums() {
        assertEquals(equal8.toString(), "8");
        assertEquals(equal63.toString(), "63");
        assertEquals(equal504.toString(), "504");
        assertEquals(equal504again.toString(), "504");
        assertEquals(equalTwenty8s.toString(), "88888888888888888888");
        assertEquals(equalTwenty8sagain.toString(), "88888888888888888888");
        assertEquals(BigNumber.toDigit(equalTwenty8sagain, 17), 8);
    }

    BigNumber onePowerZero = BigNumber.exponentiate(add1, 0);
    BigNumber fatZero = BigNumber.fromString("0");
    BigNumber zeroPowerOne = BigNumber.exponentiate(fatZero, 1);
    BigNumber eightSquared = BigNumber.exponentiate(equal8, 2);
    BigNumber three = BigNumber.fromString("3");
    BigNumber threeCubed = BigNumber.exponentiate(three, 3);
    BigNumber threePowerFour = BigNumber.exponentiate(three, 4);
    @Test
    public void testExponents() {
        assertEquals(onePowerZero.toString(), "1");
        assertEquals(zeroPowerOne.toString(), "0");
        assertEquals(eightSquared.toString(), "64");
        assertEquals(threeCubed.toString(), "27");
        assertEquals(threePowerFour.toString(), "81");
    }
}

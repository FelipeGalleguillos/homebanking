package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class CardUtilsTest {

//    @Test
//    public void cardNumberIsCreated(){
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber,is(not(emptyOrNullString())));
//    }
//
//    @Test
//    public void cardNumberIsString(){
//
//        assertThat(CardUtils.getCardNumber(),isA(String.class));
//    }
//
//    @Test
//    public void cardNumberHasSeparators(){
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber,containsString("-"));
//    }
//
//    @Test
//    public void cardNumberLength(){
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber.split(""),arrayWithSize(19));
//    }
//
//    @Test
//    public void cardCvvIsCreated(){
//        int cvv = CardUtils.getCardCvv();
//        assertThat(cvv,is(not(nullValue())));
//    }
//
//    @Test
//    public void cardCvvIsInt(){
//        assertThat(CardUtils.getCardCvv(),isA(Integer.class));
//    }
//
//    @Test
//    public void cardCvvRange(){
//        int cvv = CardUtils.getCardCvv();
//        assertThat(cvv, both(greaterThan(99)).and(lessThan(1000)));
//    }



}

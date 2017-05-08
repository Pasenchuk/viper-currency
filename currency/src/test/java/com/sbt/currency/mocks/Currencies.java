package com.sbt.currency.mocks;

import com.sbt.currency.domain.Valute;
import com.sbt.currency.interactors.CurrenciesInteractor;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */

public class Currencies {
    public static Valute RUB, USD, EUR, INR, KZT;

    public static final String CURRENCY_XML = "<?xml version=\"1.0\" encoding=\"windows-1251\" ?>" +
            "<ValCurs Date=\"06.05.2017\" name=\"Foreign Currency Market\">\n" +
            "<Valute ID=\"R01235\">\n" +
            "<NumCode>840</NumCode>\n" +
            "<CharCode>USD</CharCode>\n" +
            "<Nominal>1</Nominal>\n" +
            "<Name>Доллар США</Name>\n" +
            "<Value>58,5382</Value>\n" +
            "</Valute>" +
            "<Valute ID=\"R01239\">\n" +
            "<NumCode>978</NumCode>\n" +
            "<CharCode>EUR</CharCode>\n" +
            "<Nominal>1</Nominal>\n" +
            "<Name>Евро</Name>\n" +
            "<Value>64,2398</Value>\n" +
            "</Valute>" +
            "<Valute ID=\"R01270\">\n" +
            "<NumCode>356</NumCode>\n" +
            "<CharCode>INR</CharCode>\n" +
            "<Nominal>100</Nominal>\n" +
            "<Name>Индийских рупий</Name>\n" +
            "<Value>91,0569</Value>\n" +
            "</Valute>" +
            "<Valute ID=\"R01335\">\n" +
            "<NumCode>398</NumCode>\n" +
            "<CharCode>KZT</CharCode>\n" +
            "<Nominal>100</Nominal>\n" +
            "<Name>Казахстанских тенге</Name>\n" +
            "<Value>18,4053</Value>\n" +
            "</Valute>" +
            "</ValCurs>";

    static {

        final CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor(new MockModule());
        RUB = Valute.defaultRubInstance();

        try {
            final List<Valute> valute = currenciesInteractor.getCurrenciesFromXml(
                    CURRENCY_XML
            ).getValute();

            USD = valute.get(0);
            EUR = valute.get(1);
            INR = valute.get(2);
            KZT = valute.get(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

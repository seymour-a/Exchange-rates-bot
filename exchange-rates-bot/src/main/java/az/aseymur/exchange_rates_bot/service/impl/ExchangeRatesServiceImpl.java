package az.aseymur.exchange_rates_bot.service.impl;

import az.aseymur.exchange_rates_bot.client.CbrClient;
import az.aseymur.exchange_rates_bot.exception.ServiceException;
import az.aseymur.exchange_rates_bot.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_XPATH = "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH = "/ValCurs//Valute[@ID='R01239']/Value";
    private static final String AZN_XPATH = "/ValCurs//Valute[@ID='R01020A']/Value";

    @Autowired
    private CbrClient client;

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,EUR_XPATH);
    }

    @Override
    public String getAZNExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,AZN_XPATH);
    }


    private static String extractCurrencyValueFromXML(String xml,String xpathExpression)throws ServiceException{
     var source = new InputSource(new StringReader(xml));
     try {
         var xpath = XPathFactory.newInstance().newXPath();
         var document = (Document) xpath.evaluate("/",source, XPathConstants.NODE);
         return xpath.evaluate(xpathExpression,document);
     } catch (XPathExpressionException e){
         throw new ServiceException("Не удалось распарсить XML",e);
     }
    }
}

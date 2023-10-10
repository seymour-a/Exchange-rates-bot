package az.aseymur.exchange_rates_bot.service;

import az.aseymur.exchange_rates_bot.exception.ServiceException;

public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;
    String getEURExchangeRate() throws ServiceException;

    String getAZNExchangeRate() throws ServiceException;
}

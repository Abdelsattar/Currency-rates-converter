package com.sattar.currencyconverter.ui.currencylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sattar.currencyconverter.data.api.CurrencyApiService
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.data.model.CurrencyRatesResponse
import com.sattar.currencyconverter.di.TestSchedulerProvider
import com.sattar.currencyconverter.di.TrampolineSchedulerProvider
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Project: Currency Converter
 * Created: 12/27/2019.
 *
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModelTest {


//    private lateinit var testScheduler: TestScheduler
//    private lateinit var testSchedulerProvider: TestSchedulerProvider
//
    private var schedulerProvider = TrampolineSchedulerProvider()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var currencyApiService: CurrencyApiService

    @InjectMocks
    lateinit var currencyListRepository: CurrencyListRepository

    private lateinit var currencyListViewModel: CurrencyListViewModel

    private val currencyResponse =
        "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.6203,\"BGN\":1.9605," +
                "\"BRL\":4.8033,\"CAD\":1.5375,\"CHF\":1.1302,\"CNY\":7.9641,\"CZK\":25.777," +
                "\"DKK\":7.4746,\"GBP\":0.90039,\"HKD\":9.1543,\"HRK\":7.4519,\"HUF\":327.27," +
                "\"IDR\":17365.0,\"ILS\":4.1806,\"INR\":83.918,\"ISK\":128.11,\"JPY\":129.86," +
                "\"KRW\":1307.9,\"MXN\":22.419,\"MYR\":4.8235,\"NOK\":9.7994,\"NZD\":1.7675," +
                "\"PHP\":62.742,\"PLN\":4.3287,\"RON\":4.6496,\"RUB\":79.765,\"SEK\":10.616," +
                "\"SGD\":1.6038,\"THB\":38.221,\"TRY\":7.6465,\"USD\":1.1662,\"ZAR\":17.866}}"

    lateinit var currencyRatesResponse: CurrencyRatesResponse

    private var localCurrencies = ArrayList<CurrencyRate>()
    private val BASE_CURRENCY_CODE = "EUR"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        localCurrencies.add(
            CurrencyRate(
                "AUD", 1.25,
                "Australian Dollar", "AUD.png"
            )
        )
        localCurrencies.add(
            CurrencyRate(
                "BGN", 1.25,
                "Bulgarian Lev", "BGN.png"
            )
        )
        localCurrencies.add(
            CurrencyRate(
                "BRL", 1.25,
                "Brazilian Real", "BRL.png"
            )
        )
        localCurrencies.add(
            CurrencyRate(
                "CAD", 1.25,
                "Canadian Dollar", "CAD.png"
            )
        )

//        testScheduler = TestScheduler()
//        testSchedulerProvider = TestSchedulerProvider(testScheduler)

//        currencyListRepository = CurrencyListRepository(currencyApiService)

        currencyListViewModel =
            CurrencyListViewModel(currencyListRepository, localCurrencies, schedulerProvider)

        currencyRatesResponse = Gson().fromJson(currencyResponse, CurrencyRatesResponse::class.java)


    }

    @Test
    fun `should return the currencies`() {
        //Given
        val expectedResponse = MutableLiveData<MutableList<CurrencyRate>>()
        expectedResponse.value = localCurrencies

//        Mockito.`when`(currencyListRepository.getLatestCurrencyRates(BASE_CURRENCY_CODE))
//            .thenAnswer(Answer {
//                Single.just(currencyRatesResponse)
////
//            })
        //When
        val actualResponse = currencyListViewModel.getLatestCurrencyRates()
//        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        //Then
        Assert.assertEquals(expectedResponse.value, actualResponse.value)
    }


    @Test
    fun `should call currency repository `() {
        currencyListViewModel.getLatestCurrencyRates()
//        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        Mockito.verify(currencyListRepository.getLatestCurrencyRates(BASE_CURRENCY_CODE), times(1))
    }
}
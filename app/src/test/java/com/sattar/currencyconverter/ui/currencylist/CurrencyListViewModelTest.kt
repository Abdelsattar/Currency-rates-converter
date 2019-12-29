package com.sattar.currencyconverter.ui.currencylist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sattar.currencyconverter.data.model.CurrencyRate
import com.sattar.currencyconverter.di.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

/**
 * Project: Currency Converter
 * Created: 12/27/2019.
 *
 * @author : Mohamed Abd EL-Sattar
 */
class CurrencyListViewModelTest {


    private var testScheduler = TestScheduler()
    private var schedulerProvider = TestSchedulerProvider(testScheduler)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var currencyListRepository: CurrencyListRepository

    private lateinit var currencyListViewModel: CurrencyListViewModel

    var BASE_EURO = "EUR"

    private var localCurrenciesInfo = ArrayList<CurrencyRate>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        localCurrenciesInfo = initEUROExpectedResponse(0.0)

        currencyListViewModel =
            CurrencyListViewModel(currencyListRepository, localCurrenciesInfo, schedulerProvider)

    }

    //region test cases for getLatestCurrencyRates

    @Test
    fun `should return the currencies compiend with name and flag url`() {
        //Given.
        val expectedResponse = initEUROExpectedResponse()

        `when`(
            currencyListRepository.getLatestCurrencyRates(
                ArgumentMatchers.anyString(),
                eq(1.0)
            )
        ).thenAnswer {
            Single.just(initEUROExpectedResponse())
        }

        //When
        val actualResponse = currencyListViewModel.getLatestCurrencyRates()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        //Then
        Assert.assertEquals(expectedResponse, actualResponse.value)
        Assert.assertEquals(BASE_EURO, currencyListViewModel.baseCurrencyCode)
    }

    @Test(expected = java.lang.Exception::class)
    fun `should return the currencies compiend with name and flag error `() {

        `when`(
            currencyListRepository.getLatestCurrencyRates(
                eq("500"),
                eq(1.0)
            )
        ).thenThrow(Exception())

        currencyListViewModel.baseCurrencyCode = "500"
        currencyListViewModel.baseCurrencyRateFactor = 1.0

        currencyListViewModel.getLatestCurrencyRates()
    }

    @Test
    fun `should return the currencies with rate multiplied By factor after change the factor For EURO`() {
        //Given.
        val rateFactor = 2.0
        currencyListViewModel.baseCurrencyRateFactor = rateFactor
        val expectedResponse = initEUROExpectedResponse(rateFactor)


        val newRateFactor = 2.0
        `when`(
            currencyListRepository.getLatestCurrencyRates(
                ArgumentMatchers.anyString(),
                eq(newRateFactor)
            )
        ).thenAnswer {
            Single.just(initEUROExpectedResponse(newRateFactor))
        }
        //When
        val actualResponse = currencyListViewModel.getLatestCurrencyRates()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        //Then
        Assert.assertEquals(expectedResponse, actualResponse.value)
    }

    @Test
    fun `should call currency repository when call getLatestCurrencyRates `() {
        currencyListViewModel.getLatestCurrencyRates()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(
            currencyListRepository,
            atLeastOnce()
        ).getLatestCurrencyRates(ArgumentMatchers.anyString(), ArgumentMatchers.anyDouble())
    }

    private fun initEUROExpectedResponse(rateFactor: Double = 1.0): ArrayList<CurrencyRate> {
        val AUD_RATE = 1.1
        val BGN_RATE = 1.2
        val BRL_RATE = 1.3
        val CAD_RATE = 1.4

        val expectedResponse = ArrayList<CurrencyRate>()
        expectedResponse.add(
            CurrencyRate(
                "AUD", AUD_RATE * rateFactor,
                "Australian Dollar", "AUD.png"
            )
        )
        expectedResponse.add(
            CurrencyRate(
                "BGN", BGN_RATE * rateFactor,
                "Bulgarian Lev", "BGN.png"
            )
        )
        expectedResponse.add(
            CurrencyRate(
                "BRL", BRL_RATE * rateFactor,
                "Brazilian Real", "BRL.png"
            )
        )
        expectedResponse.add(
            CurrencyRate(
                "CAD", CAD_RATE * rateFactor,
                "Canadian Dollar", "CAD.png"
            )
        )

        return expectedResponse
    }


    //endregion
}
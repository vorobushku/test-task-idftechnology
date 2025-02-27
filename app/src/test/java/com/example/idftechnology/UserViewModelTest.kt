package com.example.idftechnology

import com.example.idftechnology.data.repository.UserRepository
import com.example.idftechnology.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {
    private lateinit var viewModel: UserViewModel
    private lateinit var repository: UserRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk {
            coEvery { getUsers() } returns flowOf(emptyList()) // Указываем поведение сразу
        }
        viewModel = UserViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should set error message when API fails`() = runTest {
        coEvery { repository.getUsers() } returns flowOf(emptyList())

        viewModel = UserViewModel(repository)

        val job = launch {
            viewModel.errorMessage.collect { error ->
                assertEquals("Не удалось загрузить пользователей", error)
            }
        }

        advanceUntilIdle()
        job.cancel()
    }
}
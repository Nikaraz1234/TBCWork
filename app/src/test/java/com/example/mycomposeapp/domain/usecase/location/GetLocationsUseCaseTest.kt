package com.example.mycomposeapp.domain.usecase.location

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Location
import com.example.mycomposeapp.domain.repository.LocationRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetLocationsUseCaseTest {

    private val repository = mockk<LocationRepository>()
    private val useCase = GetLocationsUseCase(repository)

    @Test
    fun `invoke delegates to repository and returns its flow`() = runTest {
        val upstream = emptyFlow<Resource<List<Location>>>()

        every { repository.getLocations() } returns upstream

        val result = useCase()

        assert(result === upstream)
        verify(exactly = 1) { repository.getLocations() }
    }
}
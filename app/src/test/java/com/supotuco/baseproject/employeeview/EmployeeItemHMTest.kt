package com.supotuco.baseproject.employeeview

import com.supotuco.baseproject.employee.EmployeeServerData
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmployeeItemHMTest {

    @Test
    fun items_are_same_if_employee_id_matches() {
        // Given
        val employee = createTestEmployee(uuid = "someId")
        val lhs = EmployeeItemHM(
                employee
        ) { }
        val rhs = EmployeeItemHM(
                employee
        ) { }

        // When
        val lhsHasSameAsRhs = lhs.areItemsSame(rhs)
        val rhsHasSameAsLhs = rhs.areItemsSame(lhs)

        // Then
        assertTrue(lhsHasSameAsRhs)
        assertTrue(rhsHasSameAsLhs)
    }

    @Test
    fun items_differ_if_employee_ids_differ() {
        // Given
        val firstEmployee = createTestEmployee(uuid = "1")
        val secondEmployee = createTestEmployee(uuid = "2")
        val lhs = EmployeeItemHM(
                firstEmployee
        ) { }
        val rhs = EmployeeItemHM(
                secondEmployee
        ) { }

        // When
        val lhsHasSameAsRhs = lhs.areItemsSame(rhs)
        val rhsHasSameAsLhs = rhs.areItemsSame(lhs)

        // Then
        assertFalse(lhsHasSameAsRhs)
        assertFalse(rhsHasSameAsLhs)
    }

    @Test
    fun contents_are_same_if_employees_are_same() {
        // Given
        val employee = createTestEmployee(uuid = "someId")
        val lhs = EmployeeItemHM(
                employee
        ) { }
        val rhs = EmployeeItemHM(
                employee
        ) { }

        // When
        val lhsHasSameAsRhs = lhs.areContentsSame(rhs)
        val rhsHasSameAsLhs = rhs.areContentsSame(lhs)

        // Then
        assertTrue(lhsHasSameAsRhs)
        assertTrue(rhsHasSameAsLhs)
    }

    @Test
    fun contents_differ_if_employee_data_differs() {
        // Given
        val oldEmployee = createTestEmployee(uuid = "1", fullName = "old name")
        val newEmployee = createTestEmployee(uuid = "1", fullName = "new name")
        val lhs = EmployeeItemHM(
                oldEmployee
        ) { }
        val rhs = EmployeeItemHM(
                newEmployee
        ) { }

        // When
        val lhsHasSameAsRhs = lhs.areContentsSame(rhs)
        val rhsHasSameAsLhs = rhs.areContentsSame(lhs)

        // Then
        assertFalse(lhsHasSameAsRhs)
        assertFalse(rhsHasSameAsLhs)
    }

    @Test
    fun visitor_is_skipped_if_it_does_not_implement_employee_item_visitor() {
        // Given
        val holderModel = EmployeeItemHM(createTestEmployee()) { }
        val testVisitor = FakeEmployeeItemVisitor()

        // When
        holderModel.accept(testVisitor)

        // Then
        testVisitor.assertHasVisited(holderModel)
    }

    /**
     * I'm aware mockito exists but I wanted to see this approach.
     */
    private class FakeEmployeeItemVisitor : EmployeeItemHM.Visitor {
        private var hasBeenVisited = false
        private var visitedHistory: MutableList<EmployeeItemHM> = mutableListOf()

        override fun visit(employeeItemHM: EmployeeItemHM) {
            hasBeenVisited = true
            visitedHistory.add(employeeItemHM)
        }

        fun assertHasVisited() {
            assertTrue(
                    "Expected employee visitor to visit but did not",
                    hasBeenVisited
            )
        }

        fun assertHasVisited(item: EmployeeItemHM) {
            assertHasVisited()
            assertTrue(
                    "Expected visitor to visit $item but did not",
                    visitedHistory.contains(item)
            )
        }
    }

    private fun createTestEmployee(
            uuid: String = "",
            fullName: String = "",
            phoneNumber: String? = null,
            emailAddress: String = "",
            biography: String? = null,
            photoUrlSmall: String? = null,
            photoUrlLarge: String? = null,
            team: String = "",
            type: EmployeeServerData.Type = EmployeeServerData.Type.FULL_TIME
    ): EmployeeServerData {
        return EmployeeServerData(
                uuid = uuid,
                fullName = fullName,
                phoneNumber = phoneNumber,
                emailAddress = emailAddress,
                biography = biography,
                photoUrlSmall = photoUrlSmall,
                photoUrlLarge = photoUrlLarge,
                team = team,
                type = type
        )
    }
}
package taxipark

class Task {
    /*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
    fun TaxiPark.checkParetoPrinciple(): Boolean {
        if (trips.isEmpty()) return false

        val totalIncome = trips.sumByDouble { it.cost }
        val driverIncomesDescending = trips.groupBy { it.driver }
            .map { (_, tripList) -> tripList.sumByDouble { it.cost }}
            .sortedDescending()

        val numberOfTopDrivers = (allDrivers.size * 0.2).toInt()
        val incomeOfTopDrivers = driverIncomesDescending
            .take(numberOfTopDrivers)
            .sum()

        return incomeOfTopDrivers >= totalIncome * 0.8
    }
}
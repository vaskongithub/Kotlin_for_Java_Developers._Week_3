package taxipark

import java.util.*
import kotlin.math.roundToInt

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.subtract(this.trips.map { trip -> trip.driver })

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
//    val mutableList = this.trips.flatMap { trip -> trip.passengers }.toMutableList()
//    println("All passengers in all trips $mutableList")
//    println("All passengers in taxipark ${this.allPassengers}")
//    println("flatMap $mutableList")
//    if (minTrips == 0) {
//        return this.allPassengers.toSet()
//    }
//    for (ind in 1 until minTrips) {
//        this.allPassengers.forEach {
//            mutableList.remove(it)
//        }
//        println("After $ind substraction $mutableList")
//    }
//    println("After all substractions $mutableList")
//    val set = mutableList.toSet()
//    println("ToSet $set")
//    return set

    if (minTrips == 0) {
        return this.allPassengers.toSet()
    }
    val list = this.trips
        .flatMap { trip -> trip.passengers }
    return list
        .filter { passenger ->
            val frequency = Collections.frequency(list, passenger)
            println("passenger $passenger, frequency $frequency")
            frequency >= minTrips
        }
        .toSet()
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val list = this.trips
        .filter { trip -> trip.driver == driver }
        .flatMap { trip -> trip.passengers }
    return list
        .filter { passenger -> Collections.frequency(list, passenger) > 1 }
        .toSet()
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val discountList = this.trips
        .filter { trip -> trip.discount != null && trip.discount > 0}
        .flatMap { trip -> trip.passengers }
        .toMutableList()
    println("discountList $discountList")

    val withoutDiscountList = this.trips
        .filter { trip -> trip.discount == null || trip.discount == 0.0 }
        .flatMap { trip -> trip.passengers }
    println("withoutDiscountList $withoutDiscountList")

    withoutDiscountList.forEach {
        discountList.remove(it)
    }
    println("discountList $discountList")

    val toSet = discountList.toSet()
    println("toSet $toSet")

    return toSet
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (this.trips.isEmpty()) {
        return null
    }

    val list = this.trips
        .filter { trip -> trip.duration != null }
        .map { trip ->
            trip.duration
            var start: Int = trip.duration?.div(10)?.times(10) ?: 0
            var end: Int = trip.duration?.plus(10)?.div(10)?.times(10)?.minus(1) ?: 9
            println("start $start, end $end")
            IntRange(start, end)
        }
        .toList()
    println("list $list")

    var maxBy = list
        .groupingBy { it }
        .eachCount()
        .entries
        .maxBy { it.value }
        ?.key

    println("maxBy $maxBy")

    return maxBy
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty()) {
        return false;
    }
    //sum all of all trip cost
    val sumOfAll = trips
        .sumByDouble { it.cost }
//        .map { trip -> trip.cost }
//        .reduce { acc, c -> acc + c }
    println("sumOfAll $sumOfAll")

    // trips sorted by cost
    val sortedTrips = trips
        .sortedByDescending { trip -> trip.cost }
    println("sortedTrips $sortedTrips")

//    sortedTrips.forEach { trip ->
//        println("cost ${trip.cost}")
//    }

    val groupByDriver = trips
        .groupBy { it.driver }
    println("groupByDriver $groupByDriver")

    val driversToCost = groupByDriver.mapValues {
        it.value.map { trip -> trip.cost }
            .reduce { acc, c -> acc + c }
    }
    println("driversToCost $driversToCost")

    val eightyPercents = sumOfAll * 0.8
    println("eightyPercents $eightyPercents")

    val mostSuccessfulDrivers = driversToCost.values.sortedDescending()
        .take((allDrivers.size * 0.2).roundToInt())
    println("mostSuccessfulDrivers $mostSuccessfulDrivers")

    val sumOfMostSuccessful = mostSuccessfulDrivers
        .reduce { acc, c -> acc + c }
    println("sumOfMostSuccessful $sumOfMostSuccessful")

    return sumOfMostSuccessful >= eightyPercents

//    val percentages = driversCost
//        .mapValues {
//            it.value.div(sumOfAll)
//        }
//    println("percentages $percentages")
//    val filter = percentages
//        .entries
//        .filter { entry -> entry.value > 0.8 }
//    println("filter $percentages")
//
//    if(filter.isEmpty()) {
//        return false
//    }

//    var cost = 0.0
//    mostSuccessful.forEach {
//        driversCost.get(it.driver)?.let { it1 ->
//            println("it1 $it1")
//            cost += it1
//        }
//    }
//    println("cost $cost")

//    val reduce = mostSuccessful
//        .map { trip -> trip.cost }
//        .reduce { acc, c -> acc + c }
//    println("reduce $reduce")

//    val take1 = driversCost.values.take((numOfAllTrips * 0.2).roundToInt())
//    println("take1 $take1")
//    val take = take1
//        .reduce { acc, c -> acc + c }
//    println("take $take")

//    val div = take1.div(sumOfAll)
//    println("div $div")
//
//    return div > 0.8
}
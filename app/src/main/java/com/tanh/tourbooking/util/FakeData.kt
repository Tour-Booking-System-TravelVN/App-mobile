package com.tanh.tourbooking.util

import com.tanh.tourbooking.data.model.dto.tour.Category
import com.tanh.tourbooking.data.model.dto.tour.Place
import com.tanh.tourbooking.data.model.dto.tour.Tour
import com.tanh.tourbooking.data.model.dto.tour.TourGuide
import com.tanh.tourbooking.data.model.dto.tour.TourProgram

object FakeData {

    val fakeCategories = listOf(
        Category(
            name = "Adventure",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        Category(
            name = "Historical",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        ),
        Category(
            name = "Luxury",
            image = "https://i.ibb.co/HpryKNLf/justin.jpg"
        ),
        Category(
            name = "Family",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        Category(
            name = "Business",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        ),
        Category(
            name = "Relax",
            image = "https://i.ibb.co/ch8p9Pd1/image.png"
        ),
        Category(
            name = "Adventure",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        Category(
            name = "Historical",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        )
    )

    val fakePlaces = listOf(
        Place(
            name = "Hanoi",
            country = "Vietnam",
            imageUrl = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        Place(
            name = "Phuket",
            country = "Thailand",
            imageUrl = "https://i.ibb.co/HpryKNLf/justin.jpg"
        ),
        Place(
            name = "Phuket",
            country = "Thailand",
            imageUrl = "https://i.ibb.co/ch8p9Pd1/image.png"
        )
    )

    val fakeTourPrograms = listOf(
        TourProgram(
            name = "Amazing Vietnam Tour",
            description = "Explore the beauty of Vietnam from North to South.",
            rated = 5,
            totalRate = 120,
            price = 599.99,
            image = listOf(
                "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg",
                "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
            ),
            vehicle = "Bus, Boat",
            duration = "7 days 6 nights",
            startDestination = "Hanoi",
            schedules = listOf(
                Tour(
                    name = "Hanoi City Tour",
                    description = "Visit Hoan Kiem Lake, Old Quarter, and more.",
                    rated = 4,
                    totalRate = 50,
                    price = 49.99
                ),
                Tour(
                    name = "Halong Bay Cruise",
                    description = "Enjoy a scenic cruise in one of the world's wonders.",
                    rated = 5,
                    totalRate = 70,
                    price = 199.99
                )
            ),
            maxParticipant = 25,
            tourGuide = TourGuide(
                name = "Nguyen Van A",
                id = 101,
                email = "nguyenvana@example.com",
                phone = 84901234567
            )
        ),
        TourProgram(
            name = "Grand Thailand Adventure",
            description = "Experience the vibrant culture and beautiful beaches of Thailand.",
            rated = 4,
            totalRate = 200,
            price = 799.99,
            image = listOf(
                "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg",
                "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
            ),
            vehicle = "Plane, Bus, Boat",
            duration = "10 days 9 nights",
            startDestination = "Bangkok",
            schedules = listOf(
                Tour(
                    name = "Bangkok Temple Tour",
                    description = "Discover the magnificent temples of Bangkok.",
                    rated = 4,
                    totalRate = 90,
                    price = 59.99
                ),
                Tour(
                    name = "Phuket Beach Escape",
                    description = "Relax on the stunning beaches of Phuket.",
                    rated = 5,
                    totalRate = 110,
                    price = 299.99
                )
            ),
            maxParticipant = 30,
            tourGuide = TourGuide(
                name = "Somchai Thanasuk",
                id = 202,
                email = "somchai@example.com",
                phone = 66987654321
            )
        )
    )


}
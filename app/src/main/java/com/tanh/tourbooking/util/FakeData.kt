package com.tanh.tourbooking.util

import androidx.datastore.core.Serializer
import com.tanh.tourbooking.data.model.dto.faketour.FakeBookedTour
import com.tanh.tourbooking.data.model.dto.faketour.FakeCategory
import com.tanh.tourbooking.data.model.dto.faketour.FakePlace
import com.tanh.tourbooking.data.model.dto.faketour.FakeTour
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourGuide
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourProgram
import com.tanh.tourbooking.domain.model.Discount
import com.tanh.tourbooking.presentation.detail_tour.screen.DiscountSection
import java.io.InputStream
import java.io.OutputStream

object FakeData {


    val fakeMyFakeTour = listOf(
        FakeBookedTour(
            name = "Amazing Vietnam FakeTour",
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
                FakeTour(
                    name = "Hanoi City FakeTour",
                    description = "Visit Hoan Kiem Lake, Old Quarter, and more.",
                    rated = 4,
                    totalRate = 50,
                    price = 49.99
                ),
                FakeTour(
                    name = "Halong Bay Cruise",
                    description = "Enjoy a scenic cruise in one of the world's wonders.",
                    rated = 5,
                    totalRate = 70,
                    price = 199.99
                )
            ),
            maxParticipant = 25,
            fakeTourGuide = FakeTourGuide(
                name = "Nguyen Van A",
                id = 101,
                email = "nguyenvana@example.com",
                phone = 84901234567
            ),
            status = TourStatus.UPCOMING
        ),
        FakeBookedTour(
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
                FakeTour(
                    name = "Bangkok Temple FakeTour",
                    description = "Discover the magnificent temples of Bangkok.",
                    rated = 4,
                    totalRate = 90,
                    price = 59.99
                ),
                FakeTour(
                    name = "Phuket Beach Escape",
                    description = "Relax on the stunning beaches of Phuket.",
                    rated = 5,
                    totalRate = 110,
                    price = 299.99
                )
            ),
            maxParticipant = 30,
            fakeTourGuide = FakeTourGuide(
                name = "Somchai Thanasuk",
                id = 202,
                email = "somchai@example.com",
                phone = 66987654321
            ),
            status = TourStatus.ONGOING
        ),
        FakeBookedTour(
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
                FakeTour(
                    name = "Bangkok Temple FakeTour",
                    description = "Discover the magnificent temples of Bangkok.",
                    rated = 4,
                    totalRate = 90,
                    price = 59.99
                ),
                FakeTour(
                    name = "Phuket Beach Escape",
                    description = "Relax on the stunning beaches of Phuket.",
                    rated = 5,
                    totalRate = 110,
                    price = 299.99
                )
            ),
            maxParticipant = 30,
            fakeTourGuide = FakeTourGuide(
                name = "Somchai Thanasuk",
                id = 202,
                email = "somchai@example.com",
                phone = 66987654321
            ),
            status = TourStatus.COMPLETED
        )
    )

    val fakeCategories = listOf(
        FakeCategory(
            name = "Du lịch biển",
            image = "https://i.ibb.co/bgmVWQ3y/nhatrang.webp"
        ),
        FakeCategory(
            name = "Du lịch núi",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        ),
        FakeCategory(
            name = "Du lịch văn hóa",
            image = "https://i.ibb.co/5gtMf6Ty/cultural.webp"
        ),
        FakeCategory(
            name = "Du lịch ẩm thực",
            image = "https://i.ibb.co/XffN5dRs/cuisine.jpg"
        ),
        FakeCategory(
            name = "Du lịch sinh thái",
            image = "https://i.ibb.co/nNX7XQKH/eco.jpg"
        ),
        FakeCategory(
            name = "Du lịch mạo hiểm",
            image = "https://i.ibb.co/WQ4mVyQ/adventure.webp"
        )
    )

    val fakePlacesVietNam = listOf(
        FakePlace("Hà Nội", "Vietnam", "https://i.ibb.co/kVTzZjXX/hanoi.jpg"),
        FakePlace("Hồ Chí Minh", "Vietnam", "https://i.ibb.co/20ZX6DCT/sag.jpg"),
        FakePlace("Đà Nẵng", "Vietnam", "https://i.ibb.co/kF14jPH/danang.jpg"),
        FakePlace("Huế", "Vietnam", "https://i.ibb.co/27MP6pcK/hue.jpg"),
        FakePlace("Hội An", "Vietnam", "https://i.ibb.co/CsT8SK00/hoian.jpg"),
        FakePlace("Lâm Đồng", "Vietnam", "https://i.ibb.co/Rp1L8x7y/alat.jpg"),
        FakePlace("Khánh Hòa", "Vietnam", "https://i.ibb.co/bgmVWQ3y/nhatrang.webp"),
        FakePlace("Quảng Ninh", "Vietnam", "https://i.ibb.co/Cpt4S1pd/quangninh.jpg"), // Hạ Long
        FakePlace("Ninh Bình", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Quảng Bình", "Vietnam", "https://i.ibb.co/d0T5VLGG/phongnha.jpg"), // Phong Nha
        FakePlace("Lào Cai", "Vietnam", "https://i.ibb.co/fYXgQPJJ/sapa.jpg"), // Sapa ,
    )

    val popularTour = listOf(
        FakePlace("Hà Nội", "Vietnam", "https://i.ibb.co/kVTzZjXX/hanoi.jpg"),
        FakePlace("Đà Nẵng", "Vietnam", "https://i.ibb.co/kF14jPH/danang.jpg"),
        FakePlace("Huế", "Vietnam", "https://i.ibb.co/27MP6pcK/hue.jpg"),
        FakePlace("Hội An", "Vietnam", "https://i.ibb.co/CsT8SK00/hoian.jpg"),
        FakePlace("Quảng Ninh", "Vietnam", "https://i.ibb.co/Cpt4S1pd/quangninh.jpg"), // Hạ Long
        FakePlace("Ninh Bình", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Lào Cai", "Vietnam", "https://i.ibb.co/fYXgQPJJ/sapa.jpg"), //
        )







    val fakeFakeFakeTourPrograms = listOf(
        FakeTourProgram(
            name = "Amazing Vietnam FakeTour",
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
                FakeTour(
                    name = "Hanoi City FakeTour",
                    description = "Visit Hoan Kiem Lake, Old Quarter, and more.",
                    rated = 4,
                    totalRate = 50,
                    price = 49.99
                ),
                FakeTour(
                    name = "Halong Bay Cruise",
                    description = "Enjoy a scenic cruise in one of the world's wonders.",
                    rated = 5,
                    totalRate = 70,
                    price = 199.99
                )
            ),
            maxParticipant = 25,
            fakeTourGuide = FakeTourGuide(
                name = "Nguyen Van A",
                id = 101,
                email = "nguyenvana@example.com",
                phone = 84901234567
            )
        ),
        FakeTourProgram(
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
                FakeTour(
                    name = "Bangkok Temple FakeTour",
                    description = "Discover the magnificent temples of Bangkok.",
                    rated = 4,
                    totalRate = 90,
                    price = 59.99
                ),
                FakeTour(
                    name = "Phuket Beach Escape",
                    description = "Relax on the stunning beaches of Phuket.",
                    rated = 5,
                    totalRate = 110,
                    price = 299.99
                )
            ),
            maxParticipant = 30,
            fakeTourGuide = FakeTourGuide(
                name = "Somchai Thanasuk",
                id = 202,
                email = "somchai@example.com",
                phone = 66987654321
            )
        )
    )


}
package com.tanh.tourbooking.util

import com.tanh.tourbooking.data.model.dto.faketour.FakeBookedTour
import com.tanh.tourbooking.data.model.dto.faketour.FakeCategory
import com.tanh.tourbooking.data.model.dto.faketour.FakePlace
import com.tanh.tourbooking.data.model.dto.faketour.FakeTour
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourGuide
import com.tanh.tourbooking.data.model.dto.faketour.FakeTourProgram

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
            name = "Adventure",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        FakeCategory(
            name = "Historical",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        ),
        FakeCategory(
            name = "Luxury",
            image = "https://i.ibb.co/HpryKNLf/justin.jpg"
        ),
        FakeCategory(
            name = "Family",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        FakeCategory(
            name = "Business",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        ),
        FakeCategory(
            name = "Relax",
            image = "https://i.ibb.co/ch8p9Pd1/image.png"
        ),
        FakeCategory(
            name = "Adventure",
            image = "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"
        ),
        FakeCategory(
            name = "Historical",
            image = "https://i.ibb.co/VcH7dy9d/ninhbinh2.jpg"
        )
    )

    val fakePlacesVietNam = listOf(
        FakePlace("Hà Nội", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Hồ Chí Minh", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Đà Nẵng", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Huế", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Hội An", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Lâm Đồng", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Đà Lạt
        FakePlace("Khánh Hòa", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Nha Trang
        FakePlace("Quảng Ninh", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Hạ Long
        FakePlace("Kiên Giang", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Phú Quốc
        FakePlace("Ninh Bình", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Quảng Bình", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Phong Nha
        FakePlace("Lào Cai", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"), // Sapa
        FakePlace("Thừa Thiên Huế", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Bà Rịa - Vũng Tàu", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Phú Yên", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg"),
        FakePlace("Bình Thuận", "Vietnam", "https://i.ibb.co/hJFkj2nC/ninhbinh1.jpg") // Mũi Né
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
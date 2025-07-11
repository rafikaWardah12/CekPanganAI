package com.example.cekpanganai.data.database

import com.example.cekpanganai.data.database.entity.FoodTable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FoodDataLocal2 {

    val dummyFood1 = FoodTable(
        id = "1",
        food_name = "Ayam Bakar",
        metric_serving_amount = 450,
        energy = 286.00,
        lemak = 11.34,
        lemak_jenuh = 3.10,
        lemak_tak_jenuh_ganda = 2.58,
        lemak_tak_jenuh_tunggal = 4.28,
        kolestrol = 75.00,
        protein = 25.01,
        karbohindrat = 0.00,
        serat = 0.00,
        gula = 0.00,
        sodium = 75.00,
        kalium = 229.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood2 = FoodTable(
        id = "2",
        food_name = "Ayam Geprek",
        metric_serving_amount = 300,
        energy = 789.00,
        lemak = 53.97,
        lemak_jenuh = 8.02,
        lemak_tak_jenuh_ganda = 14.22,
        lemak_tak_jenuh_tunggal = 27.57,
        kolestrol = 416.00,
        protein = 52.82,
        karbohindrat = 22.81,
        serat = 2.50,
        gula = 2.34,
        sodium = 1380.00,
        kalium = 647.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood3 = FoodTable(
        id = "3",
        food_name = "Ayam Goreng",
        metric_serving_amount = 300,
        energy = 781.00,
        lemak = 43.64,
        lemak_jenuh = 26.89,
        lemak_tak_jenuh_ganda = 4.53,
        lemak_tak_jenuh_tunggal = 8.42,
        kolestrol = 171.00,
        protein = 65.80,
        karbohindrat = 32.29,
        serat = 4.20,
        gula = 0.79,
        sodium = 1050.00,
        kalium = 969.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood4 = FoodTable(
        id = "4",
        food_name = "Bakso",
        metric_serving_amount = 108,
        energy = 218.00,
        lemak = 14.22,
        lemak_jenuh = 5.38,
        lemak_tak_jenuh_ganda = 0.63,
        lemak_tak_jenuh_tunggal = 6.06,
        kolestrol = 80.00,
        protein = 13.40,
        karbohindrat = 8.18,
        serat = 0.50,
        gula = 1.63,
        sodium = 516.00,
        kalium = 230.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood5 = FoodTable(
        id = "5",
        food_name = "Bubur Ayam",
        metric_serving_amount = 240,
        energy = 372.00,
        lemak = 12.39,
        lemak_jenuh = 3.35,
        lemak_tak_jenuh_ganda = 2.96,
        lemak_tak_jenuh_tunggal = 5.04,
        kolestrol = 72.00,
        protein = 27.56,
        karbohindrat = 36.12,
        serat = 1.90,
        gula = 0.19,
        sodium = 584.00,
        kalium = 406.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood6 = FoodTable(
        id = "6",
        food_name = "Cap Cai",
        metric_serving_amount = 90,
        energy = 60.00,
        lemak = 2.33,
        lemak_jenuh = 1.09,
        lemak_tak_jenuh_ganda = 0.18,
        lemak_tak_jenuh_tunggal = 0.90,
        kolestrol = 2.00,
        protein = 1.42,
        karbohindrat = 8.70,
        serat = 1.30,
        gula = 1.90,
        sodium = 363.00,
        kalium = 190.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood7 = FoodTable(
        id = "7",
        food_name = "Gado Gado",
        metric_serving_amount = 241,
        energy = 318.00,
        lemak = 17.86,
        lemak_jenuh = 3.34,
        lemak_tak_jenuh_ganda = 4.90,
        lemak_tak_jenuh_tunggal = 8.13,
        kolestrol = 188.00,
        protein = 17.22,
        karbohindrat = 26.28,
        serat = 6.90,
        gula = 4.43,
        sodium = 305.00,
        kalium = 751.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood8 = FoodTable(
        id = "8",
        food_name = "Kering Tempe",
        metric_serving_amount = 120,
        energy = 311.00,
        lemak = 15.52,
        lemak_jenuh = 2.60,
        lemak_tak_jenuh_ganda = 5.16,
        lemak_tak_jenuh_tunggal = 6.17,
        kolestrol = 0.00,
        protein = 16.32,
        karbohindrat = 32.33,
        serat = 2.00,
        gula = 21.61,
        sodium = 265.00,
        kalium = 519.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood9 = FoodTable(
        id = "9",
        food_name = "Kerupuk",
        metric_serving_amount = 45,
        energy = 194.00,
        lemak = 6.32,
        lemak_jenuh = 0.48,
        lemak_tak_jenuh_ganda = 1.88,
        lemak_tak_jenuh_tunggal = 3.68,
        kolestrol = 0.00,
        protein = 2.48,
        karbohindrat = 31.19,
        serat = 1.00,
        gula = 1.45,
        sodium = 172.00,
        kalium = 28.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood10 = FoodTable(
        id = "10",
        food_name = "Lele Goreng",
        metric_serving_amount = 150,
        energy = 150.00,
        lemak = 4.08,
        lemak_jenuh = 1.06,
        lemak_tak_jenuh_ganda = 0.91,
        lemak_tak_jenuh_tunggal = 1.57,
        kolestrol = 103.00,
        protein = 26.41,
        karbohindrat = 0.00,
        serat = 0.00,
        gula = 0.00,
        sodium = 72.00,
        kalium = 599.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood11 = FoodTable(
        id = "11",
        food_name = "Lontong Balap",
        metric_serving_amount = 300,
        energy = 500.00,
        lemak = 9.11,
        lemak_jenuh = 2.24,
        lemak_tak_jenuh_ganda = 3.82,
        lemak_tak_jenuh_tunggal = 2.49,
        kolestrol = 0.00,
        protein = 12.39,
        karbohindrat = 91.70,
        serat = 6.20,
        gula = 2.81,
        sodium = 638.00,
        kalium = 292.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood12 = FoodTable(
        id = "12",
        food_name = "Mie Ayam",
        metric_serving_amount = 240,
        energy = 421.00,
        lemak = 18.74,
        lemak_jenuh = 2.37,
        lemak_tak_jenuh_ganda = 5.21,
        lemak_tak_jenuh_tunggal = 9.30,
        kolestrol = 105.00,
        protein = 16.70,
        karbohindrat = 46.21,
        serat = 2.50,
        gula = 1.32,
        sodium = 36.00,
        kalium = 143.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood13 = FoodTable(
        id = "13",
        food_name = "Nasi Goreng",
        metric_serving_amount = 149,
        energy = 250.00,
        lemak = 9.28,
        lemak_jenuh = 1.69,
        lemak_tak_jenuh_ganda = 4.02,
        lemak_tak_jenuh_tunggal = 2.84,
        kolestrol = 77.00,
        protein = 9.39,
        karbohindrat = 31.38,
        serat = 1.00,
        gula = 1.13,
        sodium = 618.00,
        kalium = 152.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood14 = FoodTable(
        id = "14",
        food_name = "Nasi Kuning",
        metric_serving_amount = 105,
        energy = 100.00,
        lemak = 0.18,
        lemak_jenuh = 0.05,
        lemak_tak_jenuh_ganda = 0.05,
        lemak_tak_jenuh_tunggal = 0.06,
        kolestrol = 0.00,
        protein = 1.98,
        karbohindrat = 21.90,
        serat = 0.40,
        gula = 0.41,
        sodium = 577.00,
        kalium = 46.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood15 = FoodTable(
        id = "15",
        food_name = "Nasi Pecel",
        metric_serving_amount = 120,
        energy = 276.00,
        lemak = 11.20,
        lemak_jenuh = 1.56,
        lemak_tak_jenuh_ganda = 3.51,
        lemak_tak_jenuh_tunggal = 5.55,
        kolestrol = 0.00,
        protein = 7.90,
        karbohindrat = 38.09,
        serat = 2.50,
        gula = 11.48,
        sodium = 9.00,
        kalium = 242.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood16 = FoodTable(
        id = "16",
        food_name = "Nasi Putih",
        metric_serving_amount = 105,
        energy = 135.00,
        lemak = 0.29,
        lemak_jenuh = 0.08,
        lemak_tak_jenuh_ganda = 0.08,
        lemak_tak_jenuh_tunggal = 0.09,
        kolestrol = 0.00,
        protein = 2.79,
        karbohindrat = 29.30,
        serat = 0.40,
        gula = 0.05,
        sodium = 383.00,
        kalium = 37.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood17 = FoodTable(
        id = "17",
        food_name = "Pepes Tahu",
        metric_serving_amount = 50,
        energy = 63.00,
        lemak = 3.56,
        lemak_jenuh = 0.79,
        lemak_tak_jenuh_ganda = 1.61,
        lemak_tak_jenuh_tunggal = 0.84,
        kolestrol = 6.00,
        protein = 5.71,
        karbohindrat = 2.46,
        serat = 0.60,
        gula = 0.77,
        sodium = 296.00,
        kalium = 129.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood18 = FoodTable(
        id = "18",
        food_name = "Perkedel Kentang",
        metric_serving_amount = 75,
        energy = 107.00,
        lemak = 5.60,
        lemak_jenuh = 2.59,
        lemak_tak_jenuh_ganda = 0.58,
        lemak_tak_jenuh_tunggal = 2.05,
        kolestrol = 37.00,
        protein = 2.28,
        karbohindrat = 12.39,
        serat = 1.20,
        gula = 0.63,
        sodium = 153.00,
        kalium = 242.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood19 = FoodTable(
        id = "19",
        food_name = "Petis",
        metric_serving_amount = 15,
        energy = 19.00,
        lemak = 0.34,
        lemak_jenuh = 0.07,
        lemak_tak_jenuh_ganda = 0.13,
        lemak_tak_jenuh_tunggal = 0.08,
        kolestrol = 18.00,
        protein = 3.57,
        karbohindrat = 0.29,
        serat = 0.00,
        gula = 0.18,
        sodium = 632.00,
        kalium = 22.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood20 = FoodTable(
        id = "20",
        food_name = "Rawon",
        metric_serving_amount = 241,
        energy = 288.00,
        lemak = 17.84,
        lemak_jenuh = 6.99,
        lemak_tak_jenuh_ganda = 1.06,
        lemak_tak_jenuh_tunggal = 8.16,
        kolestrol = 69.00,
        protein = 23.13,
        karbohindrat = 8.38,
        serat = 1.10,
        gula = 1.07,
        sodium = 413.00,
        kalium = 562.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood21 = FoodTable(
        id = "21",
        food_name = "Rendang Daging",
        metric_serving_amount = 240,
        energy = 468.00,
        lemak = 26.57,
        lemak_jenuh = 15.56,
        lemak_tak_jenuh_ganda = 1.93,
        lemak_tak_jenuh_tunggal = 6.50,
        kolestrol = 69.00,
        protein = 47.23,
        karbohindrat = 10.78,
        serat = 4.20,
        gula = 3.14,
        sodium = 442.00,
        kalium = 894.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood22 = FoodTable(
        id = "22",
        food_name = "Rujak",
        metric_serving_amount = 200,
        energy = 243.00,
        lemak = 12.30,
        lemak_jenuh = 2.01,
        lemak_tak_jenuh_ganda = 5.06,
        lemak_tak_jenuh_tunggal = 4.27,
        kolestrol = 70.00,
        protein = 11.98,
        karbohindrat = 24.07,
        serat = 6.20,
        gula = 10.84,
        sodium = 278.00,
        kalium = 433.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood23 = FoodTable(
        id = "23",
        food_name = "Sambal",
        metric_serving_amount = 20,
        energy = 35.00,
        lemak = 2.00,
        lemak_jenuh = 1.00,
        lemak_tak_jenuh_ganda = 0.00,
        lemak_tak_jenuh_tunggal = 0.00,
        kolestrol = 35.00,
        protein = 1.00,
        karbohindrat = 4.00,
        serat = 1.00,
        gula = 2.00,
        sodium = 540.00,
        kalium = 65.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood24 = FoodTable(
        id = "24",
        food_name = "Sate Ayam",
        metric_serving_amount = 45,
        energy = 101.00,
        lemak = 6.67,
        lemak_jenuh = 1.54,
        lemak_tak_jenuh_ganda = 1.74,
        lemak_tak_jenuh_tunggal = 2.89,
        kolestrol = 22.00,
        protein = 8.79,
        karbohindrat = 2.19,
        serat = 0.90,
        gula = 0.89,
        sodium = 160.00,
        kalium = 142.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood25 = FoodTable(
        id = "25",
        food_name = "Sayur Asem",
        metric_serving_amount = 240,
        energy = 80.00,
        lemak = 2.76,
        lemak_jenuh = 0.37,
        lemak_tak_jenuh_ganda = 0.84,
        lemak_tak_jenuh_tunggal = 1.36,
        kolestrol = 3.00,
        protein = 3.18,
        karbohindrat = 12.90,
        serat = 2.50,
        gula = 4.75,
        sodium = 108.00,
        kalium = 261.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood26 = FoodTable(
        id = "26",
        food_name = "Sayur Lodeh",
        metric_serving_amount = 240,
        energy = 162.00,
        lemak = 9.47,
        lemak_jenuh = 6.92,
        lemak_tak_jenuh_ganda = 0.77,
        lemak_tak_jenuh_tunggal = 1.12,
        kolestrol = 7.00,
        protein = 6.66,
        karbohindrat = 14.73,
        serat = 2.50,
        gula = 4.23,
        sodium = 489.00,
        kalium = 418.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood27 = FoodTable(
        id = "27",
        food_name = "Sayur Sop",
        metric_serving_amount = 241,
        energy = 72.00,
        lemak = 1.93,
        lemak_jenuh = 0.29,
        lemak_tak_jenuh_ganda = 0.72,
        lemak_tak_jenuh_tunggal = 0.83,
        kolestrol = 0.00,
        protein = 2.12,
        karbohindrat = 11.98,
        serat = 0.70,
        gula = 3.83,
        sodium = 827.00,
        kalium = 210.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood28 = FoodTable(
        id = "28",
        food_name = "Soto Ayam",
        metric_serving_amount = 241,
        energy = 312.00,
        lemak = 14.92,
        lemak_jenuh = 3.86,
        lemak_tak_jenuh_ganda = 3.60,
        lemak_tak_jenuh_tunggal = 5.74,
        kolestrol = 129.00,
        protein = 24.01,
        karbohindrat = 19.55,
        serat = 1.70,
        gula = 0.98,
        sodium = 210.00,
        kalium = 298.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood29 = FoodTable(
        id = "29",
        food_name = "Tahu Campur",
        metric_serving_amount = 250,
        energy = 391.00,
        lemak = 10.89,
        lemak_jenuh = 1.27,
        lemak_tak_jenuh_ganda = 3.70,
        lemak_tak_jenuh_tunggal = 5.34,
        kolestrol = 0.00,
        protein = 11.26,
        karbohindrat = 62.65,
        serat = 3.50,
        gula = 4.45,
        sodium = 611.00,
        kalium = 253.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood30 = FoodTable(
        id = "30",
        food_name = "Telur Ceplok",
        metric_serving_amount = 60,
        energy = 92.00,
        lemak = 7.04,
        lemak_jenuh = 1.98,
        lemak_tak_jenuh_ganda = 1.22,
        lemak_tak_jenuh_tunggal = 2.92,
        kolestrol = 210.00,
        protein = 6.27,
        karbohindrat = 0.40,
        serat = 0.00,
        gula = 0.38,
        sodium = 94.00,
        kalium = 68.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood31 = FoodTable(
        id = "31",
        food_name = "Telur Dadar",
        metric_serving_amount = 65,
        energy = 93.00,
        lemak = 7.33,
        lemak_jenuh = 2.05,
        lemak_tak_jenuh_ganda = 1.28,
        lemak_tak_jenuh_tunggal = 3.04,
        kolestrol = 217.00,
        protein = 6.48,
        karbohindrat = 0.42,
        serat = 0.00,
        gula = 0.40,
        sodium = 98.00,
        kalium = 70.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood32 = FoodTable(
        id = "32",
        food_name = "Telur Rebus",
        metric_serving_amount = 50,
        energy = 77.00,
        lemak = 5.28,
        lemak_jenuh = 1.63,
        lemak_tak_jenuh_ganda = 0.70,
        lemak_tak_jenuh_tunggal = 2.03,
        kolestrol = 211.00,
        protein = 6.26,
        karbohindrat = 0.56,
        serat = 0.00,
        gula = 0.56,
        sodium = 139.00,
        kalium = 63.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood33 = FoodTable(
        id = "33",
        food_name = "Tempe Bacem",
        metric_serving_amount = 125,
        energy = 244.00,
        lemak = 15.50,
        lemak_jenuh = 2.75,
        lemak_tak_jenuh_ganda = 4.92,
        lemak_tak_jenuh_tunggal = 5.84,
        kolestrol = 63.00,
        protein = 18.64,
        karbohindrat = 11.16,
        serat = 0.20,
        gula = 1.96,
        sodium = 342.00,
        kalium = 421.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood34 = FoodTable(
        id = "34",
        food_name = "Tempe Goreng",
        metric_serving_amount = 85,
        energy = 192.00,
        lemak = 12.93,
        lemak_jenuh = 1.79,
        lemak_tak_jenuh_ganda = 4.20,
        lemak_tak_jenuh_tunggal = 5.61,
        kolestrol = 0.00,
        protein = 11.31,
        karbohindrat = 10.15,
        serat = 0.20,
        gula = 0.16,
        sodium = 236.00,
        kalium = 268.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood35 = FoodTable(
        id = "35",
        food_name = "Terong Balado",
        metric_serving_amount = 120,
        energy = 117.00,
        lemak = 9.58,
        lemak_jenuh = 0.73,
        lemak_tak_jenuh_ganda = 2.91,
        lemak_tak_jenuh_tunggal = 5.41,
        kolestrol = 0.00,
        protein = 1.40,
        karbohindrat = 8.51,
        serat = 4.10,
        gula = 3.80,
        sodium = 314.00,
        kalium = 288.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood36 = FoodTable(
        id = "36",
        food_name = "Nasi Tiwul",
        metric_serving_amount = 160,
        energy = 236.00,
        lemak = 0.41,
        lemak_jenuh = 0.11,
        lemak_tak_jenuh_ganda = 0.07,
        lemak_tak_jenuh_tunggal = 0.11,
        kolestrol = 0.00,
        protein = 2.01,
        karbohindrat = 56.13,
        serat = 2.70,
        gula = 2.51,
        sodium = 307.00,
        kalium = 400.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood37 = FoodTable(
        id = "37",
        food_name = "Tumis Kangkung",
        metric_serving_amount = 85,
        energy = 106.00,
        lemak = 9.40,
        lemak_jenuh = 0.70,
        lemak_tak_jenuh_ganda = 2.83,
        lemak_tak_jenuh_tunggal = 5.34,
        kolestrol = 2.00,
        protein = 2.76,
        karbohindrat = 4.31,
        serat = 1.80,
        gula = 0.55,
        sodium = 414.00,
        kalium = 456.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood38 = FoodTable(
        id = "38",
        food_name = "Urap-Urap",
        metric_serving_amount = 80,
        energy = 90.00,
        lemak = 6.12,
        lemak_jenuh = 5.31,
        lemak_tak_jenuh_ganda = 0.13,
        lemak_tak_jenuh_tunggal = 0.28,
        kolestrol = 0.00,
        protein = 1.85,
        karbohindrat = 8.71,
        serat = 3.00,
        gula = 3.39,
        sodium = 201.00,
        kalium = 239.00,
        created_at = Date(),
        updated_at = Date()
    )

    val dummyFood39 = FoodTable(
        id = "39",
        food_name = "Wedang Jahe",
        metric_serving_amount = 27,
        energy = 110.00,
        lemak = 0.00,
        lemak_jenuh = 0.00,
        lemak_tak_jenuh_ganda = 0.00,
        lemak_tak_jenuh_tunggal = 0.00,
        kolestrol = 0.00,
        protein = 0.00,
        karbohindrat = 27.00,
        serat = 0.00,
        gula = 27.00,
        sodium = 40.00,
        kalium = 0.00,
        created_at = Date(),
        updated_at = Date()
    )
}
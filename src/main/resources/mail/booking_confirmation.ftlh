<#-- booking_confirmation.ftlh -->
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Подтверждение бронирования</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            text-align: center;
        }

        .details {
            background: #fafafa;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }

        .booking {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-top: 15px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }

        .booking img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }

        .footer {
            margin-top: 20px;
            font-size: 12px;
            color: #777;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Здравствуйте, ${user.firstName}!</h2>
    <p>Ваши бронирования в нашем хостеле подтверждены.</p>

    <div class="details">
        <#list bookings as booking>
            <div class="booking">
                <img src="${booking.imageUrl}" alt="Изображение бронирования">
                <div>
                    <p><strong>Номер бронирования:</strong> ${booking.bookingNumber}</p>
                    <p><strong>Дата заезда:</strong> ${booking.entryDate}</p>
                    <p><strong>Дата выезда:</strong> ${booking.departureDate}</p>
                    <p><strong>Цена:</strong> ${booking.totalPrice} €</p>
                </div>
            </div>
        </#list>

    </div>

    <p>Если у вас есть вопросы, свяжитесь с нами.</p>

    <div class="footer">
        <p>С уважением, команда Hostel Casa Flamingo</p>
    </div>
</div>
</body>
</html>

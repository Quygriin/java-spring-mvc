(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 1
            },
            992: {
                items: 2
            },
            1200: {
                items: 2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
    });



    // Product Quantity
    // $('.quantity button').on('click', function () {
    //     var button = $(this);
    //     var oldValue = button.parent().parent().find('input').val();
    //     if (button.hasClass('btn-plus')) {
    //         var newVal = parseFloat(oldValue) + 1;
    //     } else {
    //         if (oldValue > 0) {
    //             var newVal = parseFloat(oldValue) - 1;
    //         } else {
    //             newVal = 0;
    //         }
    //     }
    //     button.parent().parent().find('input').val(newVal);
    // });

//    // Set form index
//     const index = input.attr("data-cart-detail-index");
//     const el = document.getElementById(`cartDetails[${index}].quantity`);
//     $(el).val(newVal);
     
$(document).ready(function () {
    $(".btn-outline-secondary").click(function () {
        let button = $(this);
        let quantityElement = button.siblings("span");
        let cartDetailId = quantityElement.attr("data-cart-detail-id");
        let price = parseFloat(quantityElement.attr("data-cart-detail-price"));
        let currentQuantity = parseInt(quantityElement.text());

        // Kiểm tra tăng hoặc giảm số lượng
        if (button.text() === "+") {
            currentQuantity++;
        } else if (button.text() === "-" && currentQuantity > 1) {
            currentQuantity--;
        }

        quantityElement.text(currentQuantity); // Cập nhật số lượng hiển thị
        let totalRowElement = $("td[data-cart-detail-id='" + cartDetailId + "']");
        totalRowElement.text((currentQuantity * price).toFixed(2)); // Cập nhật tổng tiền cho sản phẩm

        // Cập nhật số lượng trong form (phần dưới)
        $("input.cart-quantity-input[data-cart-detail-id='" + cartDetailId + "']").val(currentQuantity);

        // Cập nhật tổng tiền toàn bộ đơn hàng
        updateTotalPrice();
    });

    function updateTotalPrice() {
        let totalPrice = 0;
        $("td[data-cart-detail-id]").each(function () {
            totalPrice += parseFloat($(this).text());
        });

        let finalPrice = totalPrice + 3; // Cộng thêm phí vận chuyển
        $("p[data-cart-detail-price]").eq(0).find("strong").text(totalPrice.toFixed(2));
        $("p[data-cart-detail-price]").eq(1).find("strong").text(finalPrice.toFixed(2));
    }
});

$(document).ready(function () {
    // Gán sự kiện click cho nút tăng và giảm số lượng
    $(".btn-plus, .btn-minus").click(function (e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định (nếu button nằm trong form)
        
        // "this" chính là nút được click
        var button = $(this);
        // Tìm phần tử input liền kề trong cùng .input-group
        var $input = button.closest('.input-group').find("input");
        var currentQuantity = parseInt($input.val());

        // Kiểm tra nếu currentQuantity không phải số
        if (isNaN(currentQuantity)) {
            currentQuantity = 1;
        }

        // Nếu nút có class btn-plus thì tăng số lượng
        if (button.hasClass("btn-plus")) {
            currentQuantity++;
        }
        // Nếu nút có class btn-minus và currentQuantity lớn hơn 1 thì giảm số lượng
        else if (button.hasClass("btn-minus") && currentQuantity > 1) {
            currentQuantity--;
        }
        
        // Cập nhật giá trị mới vào input
        $input.val(currentQuantity);
        updateRowSubtotal($row, currentQuantity);
        // Nếu bạn có logic cập nhật giá tiền hoặc tổng đơn hàng, gọi hàm đó tại đây
        updateTotalPrice();
    });

    function updateRowSubtotal($row, quantity) {
        let price = parseFloat($row.find('td[data-price]').data('price'));
        if (!isNaN(price)) {
            let subtotal = price * quantity;
            $row.find('td[data-subtotal]').text(subtotal.toFixed(2));
        }
    }

    // Hàm cập nhật tổng tiền toàn bộ đơn hàng
    function updateTotalPrice() {
        let totalPrice = 0;
        $("td[data-cart-detail-id]").each(function () {
            totalPrice += parseFloat($(this).text());
        });

        let finalPrice = totalPrice + 3; // Cộng thêm phí vận chuyển
        $("p[data-cart-detail-price]").eq(0).find("strong").text(totalPrice.toFixed(2));
        $("p[data-cart-detail-price]").eq(1).find("strong").text(finalPrice.toFixed(2));
    }
});
    
})(jQuery);


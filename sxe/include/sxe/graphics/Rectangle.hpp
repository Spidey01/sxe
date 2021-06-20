#ifndef SXE_GRAPHICS_RECTANGLE__HPP
#define SXE_GRAPHICS_RECTANGLE__HPP
/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

namespace sxe
{
    namespace graphics
    {

        /** A simple rectangle helper class.
         * 
         * Provides a simple way to define an x,y point combined with a width
         * and height (area).
         * 
         * @see sxe::graphics::stdmathtypes;
         */
        template <typename T>
        class Rectangle
        {
          public:
            using value_type = typename T;
            using type = Rectangle<T>;

            value_type x;
            value_type y;
            value_type width;
            value_type height;

            constexpr Rectangle();
            constexpr Rectangle(const Rectangle& other) = default;

            template <typename U>
            constexpr Rectangle(const Rectangle<U>& other);

            constexpr Rectangle(T x_value, T y_value, T width_value, T height_value);

            constexpr Rectangle<T>& operator=(const Rectangle<T>& value);

            template <typename U>
            constexpr Rectangle<T>& operator=(const Rectangle<U>& value);

            /** @returns the area of the Rectangle.
             */
            constexpr T area() const;

            /** @returns the perimeter of the Rectangle.
             */
            constexpr T perimeter() const;

            /** @returns true if Rectangle is a square.
             */
            constexpr bool is_square() const;
        };

        typedef Rectangle<float> rect;
        typedef Rectangle<double> drect;
        typedef Rectangle<int> irect;
        typedef Rectangle<unsigned int> urect;

        template <typename T>
        constexpr Rectangle<T>::Rectangle()
            : x(0)
            , y(0)
            , width(0)
            , height(0)
        {}

        template <typename T>
        template <typename U>
        constexpr Rectangle<T>::Rectangle(const Rectangle<U>& other)
            : x(static_cast<T>(other.x))
            , y(static_cast<T>(other.y))
            , width(static_cast<T>(other.width))
            , height(static_cast<T>(other.height))
        {}

        template <typename T>
        constexpr Rectangle<T>::Rectangle(T x_value, T y_value, T width_value, T height_value)
            : x(x_value)
            , y(y_value)
            , width(width_value)
            , height(height_value)
        {}

        template <typename T>
        constexpr Rectangle<T>& Rectangle<T>::operator=(const Rectangle<T>& value)
        {
            x = value.x;
            y = value.y;
            width = value.width;
            height = value.height;
            return *this;
        }

        template <typename T>
        template <typename U>
        constexpr Rectangle<T>& Rectangle<T>::operator=(const Rectangle<U>& value)
        {
            x = static_cast<T>(value.x);
            y = static_cast<T>(value.y);
            width = static_cast<T>(value.width);
            height = static_cast<T>(value.height);
            return *this;
        }

        template <typename T, typename U>
        constexpr bool operator==(const Rectangle<T>& lhs, const Rectangle<U>& rhs)
        {
            return lhs.x == rhs.x && lhs.y == rhs.y && lhs.width == rhs.width && lhs.height == rhs.height;
        }

        template <typename T, typename U>
        constexpr bool operator!=(const Rectangle<T>& lhs, const Rectangle<U>& rhs)
        {
            return !(lhs == rhs);
        }

        template <typename T>
        constexpr T Rectangle<T>::area() const
        {
            return height * width;
        }

        template <typename T>
        constexpr T Rectangle<T>::perimeter() const
        {
            return 2 * (width + height);
        }

        template <typename T>
        constexpr bool Rectangle<T>::is_square() const
        {
            return height == width;
        }

    } // namespace graphics
} // namespace sxe

#endif // SXE_GRAPHICS_RECTANGLE__HPP
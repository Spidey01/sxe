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

            /** Tests if the rectangle intersects (overlaps) with this rectangle.
             * 
             * @param r the other rectangle.
             * @returns true if r intersects with this rectangle.
             */
            template<typename U>
            constexpr bool intersects(const Rectangle<U>& r);

            /** Determines the intersecting rectangle.
             * 
             * @param r the other rectangle.
             * @returns Rectangle describing the intersected portion.
             */
            template <typename U>
            constexpr Rectangle<T> intersected(const Rectangle<U>& r);
        };

        typedef Rectangle<float> rect;
        typedef Rectangle<double> drect;
        typedef Rectangle<int> irect;
        typedef Rectangle<unsigned int> urect;

        /** Simple string representation of a rectangle type.
         */
        template <typename T, typename string_type = std::string>
        string_type rectangle_to_string(const Rectangle<T>& r)
        {
            string_type s;

            s
                .append("{")
                .append("x: ")
                .append(std::to_string(r.x))
                .append(",")
                .append("y: ")
                .append(std::to_string(r.y))
                .append(",")
                .append("width: ")
                .append(std::to_string(r.width))
                .append(",")
                .append("height: ")
                .append(std::to_string(r.height))
                .append("}");

            return s;
        }

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

        template <typename T>
        template < typename U>
        constexpr bool Rectangle<T>::intersects(const Rectangle<U>& r)
        {
            return x < r.x + r.width &&
                   x + width > r.x &&
                   y < r.y + r.height &&
                   y + height > r.y;
        }

        template <typename T>
        template <typename U>
        constexpr Rectangle<T> Rectangle<T>::intersected(const Rectangle<U>& r)
        {
            typename T l1 = x;
            typename T r1 = x;
            if (width - x + 1 < 0)
                l1 = width;
            else
                r1 = width;

            typename T l2 = x;
            typename T r2 = x;
            if (r.width - r.x + 1 < 0)
                l2 = r.width;
            else
                r2 = r.width;

            if (l1 > r2 || l2 > r1)
                return Rectangle<T>();

            typename T t1 = y;
            typename T b1 = y;
            if (height - y + 1 < 0)
                t1 = height;
            else
                b1 = height;
            typename T t2 = r.y;
            typename T b2 = r.y;
            if (r.height - r.y + 1 < 0)
                t2 = r.height;
            else
                b2 = r.height;
            if (t1 > b2 || t2 > b1)
                return Rectangle<T>();

            Rectangle<T> tmp;
            tmp.x = std::max(l1, l2);
            tmp.width = std::min(r1, r2);
            tmp.y = std::max(t1, t2);
            tmp.height = std::min(b1, b2);
            return tmp;
        }

    } // namespace graphics
} // namespace sxe

#endif // SXE_GRAPHICS_RECTANGLE__HPP
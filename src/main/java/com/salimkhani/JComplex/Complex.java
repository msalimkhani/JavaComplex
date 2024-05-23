package com.salimkhani.JComplex;


/**
 * A complex number z is a number of the form z = x + yi, where x and y
 *  are real numbers, and i, is the imaginary unit, with the property i2= -1.
 */
public class Complex
{
    public static Complex Zero = new Complex(0.0, 0.0);
    public static Complex One = new Complex(1.0, 0.0);
    public static Complex ImaginaryOne = new Complex(0.0, 1.0);
    public static Complex NaN = new Complex(Double.NaN, Double.NaN);
    public static Complex Infinity = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    private double m_real = 0.0;
    private double m_imaginary = 0.0;
    public Complex(){}
    public Complex(double real, double imaginary)
    {
        this.m_imaginary = imaginary;
        this.m_real = real;
    }
    public double getReal() {return m_real;}
    public double getImaginary() {return m_imaginary;}
    public void setReal(double real){this.m_real = real;}
    public void setImaginary(double imaginary){this.m_imaginary = imaginary;}
    public void setValue(double real, double imaginary)
    {
        this.m_imaginary = imaginary;
        this.m_real = real;
    }
    @Override
    public String toString()
    {
        return "(" + m_real + getImaginarySignedString() + "i)";
    }
    private String getImaginarySignedString()
    {
        if(m_imaginary > 0)
            return "+" + m_imaginary;
        else return ""  + m_imaginary;
    }
    public boolean isReal() {return (m_imaginary == 0.0);}
    public boolean isImaginary() {return (m_imaginary != 0.0);}
    public boolean equals(double real, double imaginary){ return (this.m_real == real && this.m_imaginary == imaginary);}
    public boolean equals( Complex another){return (this.m_real == another.m_real && this.m_imaginary == another.m_imaginary);}
    public double magnitude()
    {
        return abs(this);
    }
    public static double abs( Complex value)
    {
        return Hypot(value.m_real, value.m_imaginary);
    }
    private static boolean isPositiveInfinity(double value)
    {
        return Double.isInfinite(value);
    }
    public double argument()
    {
        return Math.atan2(m_imaginary, m_real);
    }
    public Complex add( Complex right)
    {
        var c = this;
        c.m_real += right.m_real;
        c.m_imaginary += right.m_imaginary;
        return c;
    }
    public Complex addNew( Complex right)
    {
        var c = new Complex(m_real, m_imaginary);
        c.m_real += right.m_real;
        c.m_imaginary += right.m_imaginary;
        return c;
    }
    public Complex subtract(Complex right)
    {
        var c = this;
        c.m_real -= right.m_real;
        c.m_imaginary -= right.m_imaginary;
        return c;
    }
    public Complex subtractNew(Complex right)
    {
        var c = new Complex(this.m_real, this.m_imaginary);
        c.m_real -= right.m_real;
        c.m_imaginary -= right.m_imaginary;
        return c;
    }
    public Complex multiply(Complex right)
    {
        double result_realpart = (this.m_real * right.m_real) - (this.m_imaginary * right.m_imaginary);
        double result_imaginarypart = (this.m_imaginary * right.m_real) + (this.m_real * right.m_imaginary);
        this.m_real = result_realpart;
        this.m_imaginary = result_imaginarypart;
        return this;
    }
    public Complex divide(Complex right)
    {
        // Division : Smith's formula.
        double a = this.m_real;
        double b = this.m_imaginary;
        double c = right.m_real;
        double d = right.m_imaginary;

        // Computing c * c + d * d will overflow even in cases where the actual result of the division does not overflow.
        if (Math.abs(d) < Math.abs(c))
        {
            double doc = d / c;
            this.m_real = (a + b * doc) / (c + d * doc);
            this.m_imaginary = (b - a * doc) / (c + d * doc);

            return this;
        }
        else
        {
            double cod = c / d;
            this.m_real = (b + a * cod) / (d + c * cod);
            this.m_imaginary = (-a + b * cod) / (d + c * cod);

            return this;

        }
    }
    public Complex conjugate()
    {
        return new Complex(this.m_real, -this.m_imaginary);
    }

    private static double Hypot(double a, double b)
    {
        // Using
        //   sqrt(a^2 + b^2) = |a| * sqrt(1 + (b/a)^2)
        // we can factor out the larger component to dodge overflow even when a * a would overflow.

        a = Math.abs(a);
        b = Math.abs(b);

        double small, large;
        if (a < b)
        {
            small = a;
            large = b;
        }
        else
        {
            small = b;
            large = a;
        }

        if (small == 0.0)
        {
            return (large);
        }
        else if (isPositiveInfinity(large) && !Double.isNaN(small))
        {
            // The NaN test is necessary, so we don't return +inf when small=NaN and large=+inf.
            // NaN in any other place returns NaN without any special handling.
            return (Double.POSITIVE_INFINITY);
        }
        else
        {
            double ratio = small / large;
            return (large * Math.sqrt(1.0 + ratio * ratio));
        }

    }
}

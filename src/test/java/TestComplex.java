import com.salimkhani.JComplex.Complex;
import org.junit.jupiter.api.Test;

public class TestComplex
{
    @Test
    public void testComplex()
    {
        Complex c = new Complex(3, -2);

        assert c.toString().equals("(3.0-2.0i)");
    }
}

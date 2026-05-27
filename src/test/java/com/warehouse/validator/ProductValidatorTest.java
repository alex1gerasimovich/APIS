package com.warehouse.validator;

import com.warehouse.entity.Product;
import com.warehouse.exception.InvalidProductDataException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ProductValidatorTest {

    private ProductValidator validator;

    @BeforeMethod
    public void setUp() {
        validator = new ProductValidator();
    }

    @Test
    public void testValidate_ValidProduct_DoesNotThrow() throws Exception {
        validator.validate(new Product(1, "Apple", 100, 1.5, "Food"));
    }

    @Test
    public void testValidate_NullProduct_Throws() {
        Assert.assertThrows(InvalidProductDataException.class, () -> validator.validate(null));
    }

    @Test
    public void testValidate_ZeroId_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(0, "Apple", 100, 1.5, "Food")));
    }

    @Test
    public void testValidate_NegativeId_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(-1, "Apple", 100, 1.5, "Food")));
    }

    @Test
    public void testValidate_EmptyName_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, "", 100, 1.5, "Food")));
    }

    @Test
    public void testValidate_BlankName_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, "   ", 100, 1.5, "Food")));
    }

    @Test
    public void testValidate_NullName_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, null, 100, 1.5, "Food")));
    }

    @Test
    public void testValidate_NegativeQuantity_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, "Apple", -1, 1.5, "Food")));
    }

    @Test
    public void testValidate_ZeroQuantity_DoesNotThrow() throws Exception {
        validator.validate(new Product(1, "Apple", 0, 1.5, "Food"));
    }

    @Test
    public void testValidate_NegativePrice_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, "Apple", 100, -0.01, "Food")));
    }

    @Test
    public void testValidate_ZeroPrice_DoesNotThrow() throws Exception {
        validator.validate(new Product(1, "Apple", 100, 0.0, "Food"));
    }

    @Test
    public void testValidate_EmptyCategory_Throws() {
        Assert.assertThrows(InvalidProductDataException.class,
                () -> validator.validate(new Product(1, "Apple", 100, 1.5, "")));
    }

    @Test
    public void testIsValidLine_ValidLine_ReturnsTrue() {
        assertTrue(validator.isValidLine("1;Apple;100;1.5;Food"));
    }

    @Test
    public void testIsValidLine_TooFewParts_ReturnsFalse() {
        assertFalse(validator.isValidLine("Apple;100;1.5"));
    }

    @Test
    public void testIsValidLine_NonNumericId_ReturnsFalse() {
        assertFalse(validator.isValidLine("abc;Apple;100;1.5;Food"));
    }

    @Test
    public void testIsValidLine_EmptyName_ReturnsFalse() {
        assertFalse(validator.isValidLine("1;;100;1.5;Food"));
    }

    @Test
    public void testIsValidLine_NegativeQuantity_ReturnsFalse() {
        assertFalse(validator.isValidLine("1;Apple;-5;1.5;Food"));
    }

    @Test
    public void testIsValidLine_NegativePrice_ReturnsFalse() {
        assertFalse(validator.isValidLine("1;Apple;100;-1.5;Food"));
    }

    @Test
    public void testIsValidLine_NullLine_ReturnsFalse() {
        assertFalse(validator.isValidLine(null));
    }

    @Test
    public void testIsValidLine_EmptyLine_ReturnsFalse() {
        assertFalse(validator.isValidLine(""));
    }

    @Test
    public void testIsValidLine_BlankLine_ReturnsFalse() {
        assertFalse(validator.isValidLine("   "));
    }
}

package payment.persistence;

import common.DBConnection;
import payment.dto.PaymentMethod;
import payment.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class InvoiceDAO {
    public Invoice createInvoice(
            String orderNumber,
            Date invoiceDate,
            float price,
            PaymentMethod paymentMethod
    ) throws Exception {
        String invoiceNumber = "INV-" + orderNumber + "-001";
        Invoice invoice;
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement insertInvoice = conn.prepareStatement(
                        "INSERT INTO invoices (" +
                                "order_number," +
                                "invoice_number, " +
                                "invoice_date, " +
                                "total_price, " +
                                "payment_method) " +
                                "VALUES (?, ?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            insertInvoice.setString(1, orderNumber);
            insertInvoice.setString(2, invoiceNumber);
            insertInvoice.setDate(3, invoiceDate);
            insertInvoice.setFloat(4, price);
            insertInvoice.setString(5, paymentMethod.name());

            insertInvoice.executeUpdate();
            ResultSet rs = insertInvoice.getGeneratedKeys();

            if (rs.next()) {
                int invoiceId = rs.getInt(1);
                invoiceId = invoiceId + 1;
                invoice = new Invoice(
                        invoiceId,
                        invoiceNumber,
                        orderNumber,
                        invoiceDate,
                        price,
                        paymentMethod
                );
                return invoice;
            } else {
                throw new Exception("Failed to insert invoice");
            }
        } catch (Exception ex) {
            throw new Exception("Failed to insert invoice", ex);
        }
    }
}

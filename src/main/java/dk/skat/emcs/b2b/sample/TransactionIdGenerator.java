package dk.skat.emcs.b2b.sample;

import java.util.UUID;

/**
 * TransactionIdGenerator
 *
 * @author SKAT
 * @since 1.2
 */
public class TransactionIdGenerator {

    /**
     * Generate transaction id.
     *
     * If JVM parameter -Ddk.skat.emcs.b2b.sample.TXID_PREFIX=SOMETHING has been set generated transaction
     * id will be prefix with 'SOMETHING'. This feature is designed to make
     * diagnostics server side easier when searching the logs.
     *
     * @return Transaction id (possible prefixed)
     */
    public static final String getTransactionId() {
        final String prefix = System.getProperty("dk.skat.emcs.b2b.sample.TXID_PREFIX");
        String transactionId = UUID.randomUUID().toString();
        if (prefix != null)  {
            transactionId = prefix + transactionId;
        }
        return transactionId;
    }

    private TransactionIdGenerator() {
    }
}

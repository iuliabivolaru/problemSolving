package bvb;

/**
 * Created by iuliab on 17.12.2017.
 */
public class ProducedElement {

    private long sequenceNumber;

    ProducedElement(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    long getSequenceNumber() {
        return sequenceNumber;
    }
}

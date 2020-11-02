To re-generate the Java classes when the schema changes:

1. Download the schema and update MakePayment.xsd in this folder.
Download the schema from:
https://messaging-dev.health.state.mn.us/finance/epayment/makepayment/xsd

2. Set PATH to include the Java Home/bin folder if necessary

3. Run the JAXB CLI to generate the Java classes using the following command:
eversd1@EVERSD1-6PVSSC2 C:\mdhapps\workspace\body-art\body-art-service\src\main\resources\xsd> xjc -d ../../java -p us.mn.state.health.hrd.bodyart.payment.domain.usbank MakePayment.xsd

#!/bin/sh

xmllint --noout --schema schema/ie705.xsd ie705.xml
xmllint --noout --schema schema/ie815.xsd ie815.xml
xmllint --noout --schema schema/ie815.xsd ie815-no-delegation.xml
xmllint --noout --schema schema/ie815.xsd ie815-trigger-error-104.xml
xmllint --noout --schema schema/ie815.xsd ie815-valid-delegation.xml
xmllint --noout --schema schema/ie818.xsd ie818.xml
xmllint --noout --schema schema/ie810.xsd ie810.xml
xmllint --noout --schema schema/ie813.xsd ie813.xml
xmllint --noout --schema schema/ie819.xsd ie819.xml
xmllint --noout --schema schema/ie825.xsd ie825.xml
xmllint --noout --schema schema/ie837.xsd ie837.xml
xmllint --noout --schema schema/ie871.xsd ie871.xml

# Comment out to verify validation failure
# xmllint --noout --schema schema/ie815.xsd sample/ie815-invalid.xml

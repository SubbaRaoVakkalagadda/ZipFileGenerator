# ZipFileGenerator
Generates zip file(s) of 1 GB content(if unzipped) from a given file

# Building
mvn clean package

# Running
Go to the artifact's(ZipFileGenerator-1.0-SNAPSHOT) directory and run the following command
java -jar ZipFileGenerator-1.0-SNAPSHOT sourceFileh targetFile unzippedFileSizeInBytes noOfZipFiles zipperType

Example: Generates 5 zip files using LZ4 algorithm. The unzipped file size will be around 1 GB (1073741824 = 1024*1024*104 bytes)
java -jar ZipFileGenerator-1.0-SNAPSHOT.jar /users/subbaraov/Downloads/sample-s3.log /users/subbaraov/Downloads/sample-s3_zip 1073741824 5 lz4

#ZipperTypes
GZIP/JAVA7/LZ4


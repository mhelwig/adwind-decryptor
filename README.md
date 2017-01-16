# AdWind Decryptor

Simple decryptor for encrypted files in the AdWind/jRAT/jBifrost Java RAT.

## Usage

    usage: java -jar AdWindDecryptor
     -a,--aeskeyfile <arg>   AES key file path
     -i,--input <arg>        input file path
     -o,--output <arg>       decrypted output file path
     -r,--rsakeyfile <arg>   serialized RSA KeyRep file path

## Example

    java -jar AdWindDecryptor.jar -a mega.download -r sky.drive -i drop.box -o decrypted-file

## WARNING
This decryptor unserializes an object that is part of the malware. Therefore, it is possible that the the decryptor could get hijacked by the malware. Only use in an isolated environment and with caution.

## TODOs
- Avoid unserializing the object


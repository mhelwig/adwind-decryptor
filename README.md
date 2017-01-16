# AdWind Decryptor

Simple decryptor for crypted files within the AdWind/jRAT/jBifrost Java RAT.

## Usage

    java -jar AdWindDecryptor.jar -a /home/michael/Disassembly/Malware/AdWind/unzip/mega.download -p /home/michael/Disassembly/Malware/AdWind/unzip/sky.drive -i /home/michael/Disassembly/Malware/AdWind/unzip/drop.box -o outputfile.txt

## WARNING
This decryptor unserializes an object that is part of the malware. Therefore, it is possible that the the decryptor could get hijacked by the malware. Only use in an isolated environment and with caution.

## TODOs
- Avoid unserializing the object


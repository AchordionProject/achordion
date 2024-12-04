# achordion

## Overview
Achordion is a audio analysis system designed to recognize musical notes and chords from WAV files.
By utilizing Fast Fourier Transform (FFT) algorithms, the project accurately identifies individual notes and chords played on a guitar, providing valuable feedback for musicians and learners.
Achordion enhances musical engagement by converting audio input into a structured representation of notes and chords, making it an essential tool for music practice and analysis.

- **WAV File Input**: Accepts audio files in the WAV format.
- **Note Detection**: Analyzes the audio to identify individual notes.
- **Chord Recognition(In progress)**: Determines the chords present in the audio file.
- **JavaFX User Interface**: Provides a user-friendly interface built with JavaFX.

## Requirements

1. 21 Java compiler
2. Python 3.12.7

## Quick start

``` sh
# clone this repo
git clone https://github.com/AchordionProject/achordion.git
```

### Server
``` sh
# NOTE: The commands for running on Windows may slightly differ.
# Unix
cd server
python3 -m venv venv
source venv/bin/activate
pip3 install -r requirements.txt
python3 main.py
# Windows
# use python.exe and pip.exe instead
```

### Client
``` sh
cd client
# Unix
./mvnw clean javafx:run

# Windows
mvnw.cmd clean javafx:run
```

Alternatively you can download the archive with jar file from Releases and run it with
``` sh
java -jar <name-of-jar-file>
```

## Technologies used

### Client
1. Java
2. JavaFX - GUI library

### Server
1. Python
2. asyncio(stdlib) - for concurrency
3. librosa - for FFT & analysis algorithms



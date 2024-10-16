#include <iostream>
#include <cmath>
#include <vector>
#include <string>

// Reference frequency for A4
const double A4_FREQ = 440.0;

// Function to calculate the frequency of a note given its half steps from A4
double calculateFrequency(int halfStepsFromA4) {
    return A4_FREQ * pow(2.0, halfStepsFromA4 / 12.0);
}

// Function to print the frequencies for all 7 octaves of piano keys (starting from C and ending at B)
void assignPianoKeysToFrequencies() {
    // Vector of pairs (note, half steps from A4), ordered from C to B
    std::vector<std::pair<std::string, int>> noteMap = {
        {"C", -9}, {"C#", -8}, {"Db", -8}, {"D", -7}, {"D#", -6}, {"Eb", -6},
        {"E", -5}, {"F", -4}, {"F#", -3}, {"Gb", -3}, {"G", -2}, {"G#", -1},
        {"Ab", -1}, {"A", 0}, {"A#", 1}, {"Bb", 1}, {"B", 2}
    };

    for (int octave = 1; octave <= 7; ++octave) {
        std::cout << "Octave " << octave << " frequencies:\n";
        for (const auto& notePair : noteMap) {
            std::string note = notePair.first;
            int halfStepsFromA4 = notePair.second;

            // Calculate how far the note is from A4 in terms of octaves
            int totalHalfSteps = halfStepsFromA4 + (octave - 4) * 12;
            double frequency = calculateFrequency(totalHalfSteps);

            std::cout << note << octave << ": " << frequency << " Hz\n";
        }
        std::cout << "----------------------------\n";
    }
}

int main() {
    assignPianoKeysToFrequencies();
    return 0;
}

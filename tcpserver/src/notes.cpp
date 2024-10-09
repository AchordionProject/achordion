#include <iostream>
#include <cmath>
#include <map>

#define A4_FREQ 440.0


enum class Interval: int {
    P1 = 0, m2, M2, m3, M3, P4, TT, P5, m6, M6, m7, M7, P8,
};

enum class Notes : int{
    A, As, Bb, B, C, Cs, Db, D, Ds, Eb, E, F, Fs, Gb, G, Gs, Ab

};

std::string note_to_string(Notes note)
{
    switch (note) {
    case Notes::A: return "A";
    case Notes::As: return "A#";
    case Notes::Bb: return "Bb";
    case Notes::B: return "B";
    case Notes::C: return "C";
    case Notes::Cs: return "C#";
    case Notes::Db: return "Db";
    case Notes::D: return "D";
    case Notes::Ds: return "D#";
    case Notes::Eb: return "Eb";
    case Notes::E: return "E";
    case Notes::F: return "F";
    case Notes::Fs: return "F#";
    case Notes::Gb: return "Gb";
    case Notes::G: return "G";
    case Notes::Gs: return "G#";
    case Notes::Ab: return "Ab";
    default: return "";
    }
}


double calculate_frequency(Interval interval)
{
    return A4_FREQ * pow(2.0, static_cast<int>(interval) / 12.0);
}

int main()
{
    std::map<Notes, Interval> note_map = {
        {Notes::A, Interval::P1}, {Notes::As, Interval::m2}, {Notes::Bb, Interval::M2},
        {Notes::B, Interval::M2}, {Notes::C, Interval::m3}, {Notes::Cs, Interval::M3},
        {Notes::Db, Interval::M3}, {Notes::D, Interval::P4}, {Notes::Ds, Interval::TT},
        {Notes::Eb, Interval::TT}, {Notes::E, Interval::P5}, {Notes::F, Interval::m6},
        {Notes::Fs, Interval::M6}, {Notes::Gb, Interval::M6}, {Notes::G, Interval::m7},
        {Notes::Gs, Interval::M7}, {Notes::Ab, Interval::M7}
    };
    for(auto [note, interval]: note_map){
        double frequency = calculate_frequency(interval);
        std::cout << note_to_string(note) << ": " << frequency << " Hz" << std::endl;
    }
}

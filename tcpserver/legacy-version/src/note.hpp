#pragma once
#include <iostream>
#include <cinttypes>
#include <string>
#include <vector>
#include <cmath>
// D A D F#

#define BASE_FREQ 440.0f

class Note
{

public:
    enum class Interval: uint8_t {
        Unison = 0,
        m2,
        M2,
        m3,
        M3,
        P4,
        TT,
        P5,
        m6,
        M6,
        m7,
        M7,
        Octave,
    };
    enum class BaseNote: int8_t { A = 0, B = 2, C = 3, D = 5, E = 7, F = 8, G = 10 };
    enum class Accidental : int8_t { Flat = -1, Natural = 0, Sharp = 1 };

private:
    BaseNote bnote;
    Accidental accidental;

public:
    Note(BaseNote bnote, Accidental accidental)
        : bnote(bnote), accidental(accidental) {}

    int get_halfsteps_from_base();

    std::string to_string();

    static int halfsteps_from_a4(int frequency);

    static Note get_note_from_halfsteps(int halfsteps);

    static std::vector<Note> get_notes(const std::vector<int>& frequencies);



};

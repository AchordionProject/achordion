#pragma once
#include <cinttypes>

// D A D F#

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

    Note(BaseNote bnote, Accidental accidental)
        : bnote(bnote), accidental(accidental) {}

    int get_halfsteps_from_base() {
        return static_cast<int8_t>(this->bnote) + static_cast<int8_t>(this->accidental);
    }

    static Interval get_interval_between(Note& note1, Note& note2) {
        int halfsteps = (note2.get_halfsteps_from_base() - note1.get_halfsteps_from_base() + 12) % 12;
        return static_cast<Interval>(halfsteps);
    }

private:
    BaseNote bnote;
    Accidental accidental;
};

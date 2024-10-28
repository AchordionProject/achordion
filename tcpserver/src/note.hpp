#pragma once
#include <cinttypes>
#include <string>
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

    int get_halfsteps_from_base() {
        return static_cast<int8_t>(this->bnote) + static_cast<int8_t>(this->accidental);
    }

    std::string to_string() {
        std::string srepr;
        switch(this->bnote) {
        case BaseNote::A:
            { srepr.push_back('A'); }
            break;
        case BaseNote::B:
            { srepr.push_back('B'); }
            break;
        case BaseNote::C:
            { srepr.push_back('C'); }
            break;
        case BaseNote::D:
            { srepr.push_back('D'); }
            break;
        case BaseNote::E:
            { srepr.push_back('E'); }
            break;
        case BaseNote::F:
            { srepr.push_back('F'); }
            break;
        case BaseNote::G:
            { srepr.push_back('G'); }
            break;
        default:
            { srepr.push_back('N'); }
        }
        switch(this->accidental) {
        case Accidental::Flat:
            { srepr.push_back('b'); }
            break;
        case Accidental::Sharp:
            { srepr.push_back('#'); }
            break;
        }
        return srepr;
    }

    static int halfsteps_from_a4(int frequency){
        return static_cast<int>(round(12 * log2(frequency / BASE_FREQ)));
    }

    static Note get_note_from_halfsteps(int halfsteps) {
        // Normalize within 0 to 11 for chromatic scale
        int8_t position = abs(halfsteps % 12);

        for (auto base : {BaseNote::A, BaseNote::B, BaseNote::C, BaseNote::D, BaseNote::E, BaseNote::F, BaseNote::G}) {
            int8_t baseValue = static_cast<int8_t>(base);
            int8_t difference = position - baseValue;

            if (difference == 0) {
                return {base, Accidental::Natural};
            } else if (difference == 1) {
                return {base, Accidental::Sharp};
            } else if (difference == -1) {
                return {base, Accidental::Flat};
            }
        }
        int pos = position;
        std::cout << "Couldn't find position" << pos << std::endl;
        return {BaseNote::A, Accidental::Natural};
    }

    static std::vector<Note> get_notes(const std::vector<int>& frequencies) {
        std::vector<Note> notes;
        for(int freq: frequencies) {
            int halfsteps = halfsteps_from_a4(freq);
            Note note  = get_note_from_halfsteps(halfsteps);
            notes.push_back(note);
        }
        return notes;
    }

};

#include "note.hpp"


int Note::get_halfsteps_from_base() {
    return static_cast<int8_t>(this->bnote) + static_cast<int8_t>(this->accidental);
}

std::string Note::to_string() {
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


int Note::halfsteps_from_a4(int frequency){
    return static_cast<int>(round(12 * log2(frequency / BASE_FREQ)));
}


Note Note::get_note_from_halfsteps(int halfsteps) {
    // Normalize within 0 to 11 for chromatic scale
    int8_t position = ((halfsteps % 12) + 12) % 12;

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

std::vector<Note> Note::get_notes(const std::vector<int>& frequencies) {
    std::vector<Note> notes;
    for(int freq: frequencies) {
        int halfsteps = halfsteps_from_a4(freq);
        Note note  = get_note_from_halfsteps(halfsteps);
        notes.push_back(note);
    }
    return notes;
}

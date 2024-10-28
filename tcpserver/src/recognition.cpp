#include "recognition.hpp"

void Recognizer::store_fft(const std::vector<uint8_t>& input)
{
    std::array<kiss_fft_cpx, N> fft_average = {};
    const double NUM_CHUNKS = input.size() / N;

    for(size_t i = 0; i < NUM_CHUNKS; i++) {
        std::array<kiss_fft_cpx, N> fft_input = {};

        for(size_t n_sample = 0; n_sample < N; n_sample++) {
            int16_t sample = static_cast<int16_t>(
                                                  (input[(i * N) + n_sample * 2] << 8) |
                                                  (input[(i * N) + n_sample * 2 + 1])
                                                  );
            fft_input[n_sample].r = sample / TWO_BYTE_MAX;
            fft_input[n_sample].i = 0.0f;

        }
        std::array<kiss_fft_cpx, N> fft_output;
        kiss_fft(this->cfg, fft_input.data(), fft_output.data());

        for(size_t i = 0; i < N; i++) {
            fft_average[i].r += fft_output[i].r;
            fft_average[i].i += fft_output[i].i;
        }

    }

    for(size_t i = 0; i < N; i++) {
        fft_average[i].r /= NUM_CHUNKS;
        fft_average[i].i /= NUM_CHUNKS;
    }
    this->out = fft_average;
}

std::vector<int> Recognizer::detect_peaks()
{
    std::vector<float> magnitudes(Nd2);
    for(size_t i = 0; i < Nd2; i++) {
        magnitudes[i] = std::sqrt(this->out[i].r * this->out[i].r +
                                  this->out[i].i * this->out[i].i);
    }
    std::vector<int> peak_indices;
    for(size_t i = 1; i < Nd2 - 1; i++) {
        float dl = magnitudes[i] - magnitudes[i - 1];
        float dh = magnitudes[i] - magnitudes[i + 1];
        if(dl > DELTA && dh > DELTA) {
            peak_indices.push_back(i);
        }
    }
    return peak_indices;
}

#!/usr/bin/env python3
"""Simple stress detection hook.

When stress is detected, the program emits a calming mantra.
The available mantras are stored in `mantra_bank.json`.
"""
import json
import os

MANTRA_FILE = os.path.join(os.path.dirname(__file__), '..', 'mantra_bank.json')


def load_mantras():
    """Load the mantra bank from the JSON file."""
    with open(MANTRA_FILE, 'r', encoding='utf-8') as fh:
        data = json.load(fh)
    return data.get("mantra_bank", [])


def stress_detected():
    """Hook called when stress is detected."""
    mantras = load_mantras()
    mantra = mantras[0] if mantras else "Hakuna Matata"
    print(mantra)


if __name__ == "__main__":
    stress_detected()

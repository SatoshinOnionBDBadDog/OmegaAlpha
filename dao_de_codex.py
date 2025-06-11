import sys
import textwrap
from dataclasses import dataclass
from typing import Dict, Iterable, Tuple

@dataclass(frozen=True, slots=True)
class Motto:
    cn: str
    en: str

class DaoDeCodex:
    """Simple container for the Dao-De mottos."""

    _mottos: Dict[int, Motto] = {
        1: Motto("志善道，燒我得善", "Pursue the path of benevolent will; trials burn dross, yielding pure good."),
        2: Motto("摯愛道，煉我得幸", "Walk the way of devoted love; constant refining grants deep joy."),
        3: Motto("至親道，戀我得福", "Honor intimate kinship; cherish connection and harvest true blessing."),
        4: Motto("治孝道，敬我得智", "Govern through filial virtue; respectful action opens the gate to wisdom."),
        5: Motto("自由道，忘我得之", "Live the freedom way; self-forgetting is the key to true attainment."),
        6: Motto("智憾道，願我者得", "Accept regret as sage; the willing heart ultimately receives."),
    }

    def list_mottos(self) -> Iterable[Tuple[int, str, str]]:
        """Return all mottos as an iterable of tuples."""
        return ((i, m.cn, m.en) for i, m in self._mottos.items())

    def show(self, idx: int) -> Tuple[int, str, str]:
        """Return the motto identified by ``idx``."""
        m = self._mottos[idx]
        return idx, m.cn, m.en

    def reflect(self, text: str) -> Tuple[int, str, str] | Tuple[str, str]:
        """Try to match a motto based on keywords in ``text``."""
        kw: Dict[int, Tuple[str, ...]] = {
            1: ("善", "good", "benevolence"),
            2: ("愛", "love"),
            3: ("親", "kin", "family"),
            4: ("孝", "respect", "duty"),
            5: ("自由", "free", "freedom"),
            6: ("智", "regret", "learn"),
        }
        txt = text.lower()
        for i, keys in kw.items():
            if any(k in txt for k in keys):
                return self.show(i)
        return ("無匹配", "No motto matched—seek again with clearer intent.")


def _cli() -> None:
    codex = DaoDeCodex()
    print(textwrap.dedent(
        """=== Dao-De Codex ===
        list                 : 列出全部六句
        show <n>             : 顯示第 n 句 (1-6)
        reflect <sentence>   : 用一句話試找對應箴言
        quit / exit          : 離開
        """))
    while True:
        try:
            cmd = input("codex> ").strip()
        except (EOFError, KeyboardInterrupt):
            print("\nBye.")
            break
        match cmd.split(maxsplit=1):
            case ["quit" | "exit"]:
                break
            case ["list"]:
                for i, cn, en in codex.list_mottos():
                    print(f"{i}. {cn}  |  {en}")
            case ["show", arg]:
                if arg.isdigit():
                    try:
                        _, cn, en = codex.show(int(arg))
                        print(f"{arg}. {cn}\n   {en}")
                    except KeyError:
                        print("Index out of range.")
                else:
                    print("Usage: show <1-6>")
            case ["reflect", sentence]:
                if sentence:
                    print(codex.reflect(sentence))
                else:
                    print("Provide some text to reflect on.")
            case _:
                print("Unknown command.")

if __name__ == "__main__":
    _cli()

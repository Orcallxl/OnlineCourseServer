# -*- coding: utf-8 -*-
import json
import sys
import time
import psutil, os
from subprocess import Popen, PIPE, STDOUT

errorType = ["Accept", "RuntimeError", "TimeLimitExceeded", "MemoryLimitExceeded", "WrongAnswer", "OutputLimitExceeded"]

def judge_program(compiler, fin_str, std_out_str, timeLimit, memoryLimit,seqId):
    answer = 0
    fout = b"1"
    ferr = b"1"
    fin = str.encode(fin_str)
    std_out = std_out_str
    time_limit = float(timeLimit) / 1000  # second 时间限制
    mem_limit = float(memoryLimit) * 1024 # kb 内存限制

    problem_info = {}  # 时间单位ms 内存单位kb
    if compiler == 0:
        if os.path.exists("./"+seqId+"/main.py"):
            p = Popen("python "+"./"+seqId+"/main.py", stdin=PIPE, stdout=PIPE, stderr=PIPE)
        else:
            return ['RunFileNotFound', 0, 0, 0, 0]
    # cwd设置工作目录
    elif compiler == 1:
        if os.path.exists("./"+seqId+"/a.exe") == 0:
            return ['RunFileNotFound', 0, 0, 0, 0]
        else:
            p = Popen("./"+seqId+"/a.exe", stdin=PIPE, stdout=PIPE, stderr=PIPE)
    start = time.time()  # 开始时间
    pid = p.pid
    glan = psutil.Process(pid)  # 监听控制进程
    is_running = False
    time_now = 0  # 运行时间
    max_rss = 0  # 运行内存
    while True:
        try:
            m_infor = glan.memory_info()
        except:
            break
        rss = m_infor[0]  # 获取程序占用内存空间 rss
        if max_rss < rss:
            max_rss = rss
        if max_rss > mem_limit:  # 内存超限
            answer = 3
            break
        if not is_running:
            fout, ferr = p.communicate(input=fin)
            is_running = True
        time_now = time.time() - start  # ??
        try:
            if psutil.pid_exists(pid) is False:  # 运行结束
                break
        except:
            break

        if p.poll() == 0:  # 运行正常结束，跳出循环，继续判断
            time_now = time.time() - start
            break

        if time_now > time_limit:  # 时间超限
            answer = 2
            try:
                glan.terminate()
                break
            except:
                break

    problem_info['time'] = time_now * 1000
    problem_info['memory'] = max_rss / 1024.0
    import ctypes
    ctypes.windll.kernel32.TerminateProcess(int(p._handle), -1)

    out = bytes.decode(fout).replace('\r\n', '\n')

    ans = {}
    if answer == 2:
        ans['result'] = errorType[answer]
        ans['time'] = problem_info['time']
        ans['mem'] = problem_info['memory']
        ans['stdOut'] = out
        ans['stdErr'] = bytes.decode(ferr)
        return ans
    if answer == 3:
        ans['result'] = errorType[answer]
        ans['time'] = problem_info['time']
        ans['mem'] = problem_info['memory']
        ans['stdOut'] = out
        ans['stdErr'] = bytes.decode(ferr)
        return ans
    if std_out == out:
        answer = 0
    else:
        answer = 4
    if answer != 0 and problem_info['time'] >= time_limit * 1000:
        answer = 2
    if answer != 0 and problem_info['memory'] >= mem_limit / 1024.0:
        answer = 3
    ans['result'] = errorType[answer]
    ans['time'] = problem_info['time']
    ans['mem'] = problem_info['memory']
    ans['stdOut'] = out
    ans['stdErr'] = bytes.decode(ferr)
    return ans


if __name__ == '__main__':
    res = judge_program(int(sys.argv[1]),sys.argv[2],sys.argv[3],float(sys.argv[4]),float(sys.argv[5]),sys.argv[6])
    print(json.dumps(res))

    # print(judge_program(0, "7000 0 0 8", "6999.59327 79.99845 -0.08134 7.99954\r\n6959.38049 798.45244 -0.81131 7.95361\r\n3411.38618 6617.15599 -6.32660 4.14374\r\n6999.73385 -64.71291 0.06580 7.99970\r\n", [10000, 10000]))

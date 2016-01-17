#!/usr/bin/python
import os,re,sys

fp = sys.argv[1]
svg = open(fp, "rb")
lines = svg.readlines()
read_next = False
info = []

def get_translation(string):
    m = re.search("\([0-9-,\.]*", string)
    if(m == None):
        return '0.0,0.0'
    return m.group(0)[1:]

def get_translated_values(x,x_trans, y_trans, x_untrans, y_untrans, xp=0, yp=0):
    xs = ''
    x = x.lstrip().rstrip()
    x = x.replace(' ', ',')
    m2 = re.split(',', x)
    t = 0 
    for a in m2:
        if a == ' ' or a == '':
            continue
        if(t % 2 == 0):
            xs += str(float(a) + x_trans + x_untrans + xp) + ','
        else:
            xs += str(float(a) + y_trans + y_untrans + yp) + ','
        t += 1
    return (xs[:-1], m2)

def get_values(x):
    x = x.lstrip().rstrip()
    x = x.replace(' ', ',')
    m2 = re.split(',', x)
    xs = []
    for a in m2:
        if(a != '' and a != ' '):
            xs.append(float(a))
    return xs

def get_prev_value(i, curves, lines, moves):
    val = ''
    if(curves.has_key(i-1)):
        val = get_values(curves.get(i-1))
    elif(lines.has_key(i-1)):
        val = get_values(lines.get(i-1))
    elif(moves.has_key(i-1)):
        val = get_values(moves.get(i-1))
    return val
    
i = 0 
translate = ""
untranslate = {}
for l in lines:
    if("transform=\"translate(" in l and not read_next):
        translate = l
    if("<path" in l):
        read_next = True
        info.append([])
    elif (read_next):
        if("transform=\"translate(" in l):
            untranslate[i] = l
        if("/>" in l):
            read_next = False
            if(not untranslate.has_key(i)):
                untranslate[i] = ''
            i += 1
            continue
        info[i].append(l)

translate = get_translation(translate)
tmptrans = re.split(",", translate)
x_trans = float(tmptrans[0])
y_trans = float(tmptrans[1])

x_untrans = {}
y_untrans = {}
for i, x in untranslate.iteritems():
    x = get_translation(x)
    tmpuntrans = re.split(",", x)
    x_untrans[i] = float(tmpuntrans[0])
    y_untrans[i] = float(tmpuntrans[1])

print str(x_trans) + ' ' + str(y_trans)
print str(x_untrans) + ' ' + str(y_untrans)
stroke = []
curve = []
for ls in info:
    strokeIn = False
    for l in ls:
        l = l.lstrip()
        if("style=" in l):
            m = re.search("stroke:#[a-zA-Z0-9]*;", l)
            stroke.append(m.group(0))
            strokeIn = True
        elif("id=" not in l and strokeIn and "d=" in l):
            m = re.search("\".*\"", l)
            curve.append(m.group(0)[1:-1])
k = 0
i = 0
curves_itr = 0
tmp = []
for c in curve:
    if 'm' in c or 'M' in c:
        c = c.replace("z","")
        c = c.replace("Z","")
        tmp.append(c)
curve = tmp
for c in curve:
    curves = {}
    moves = {}
    lines = {}
    matches = -1
    copy_to_m = False
    copy_to_c = False
    copy_to_l = False
    curves_relative = {}
    moves_relative = {}
    lines_relative = {}
    commas_detected = 0
    c = c.replace(' ', ',')
    for i in xrange(0, len(c)):
        char = c[i]
        if char == 'm':
            matches += 1         
            copy_to_c = False
            copy_to_l = False
            copy_to_m = True
            moves[matches] = ''
            moves_relative[matches] = True
        if char == 'M':
            matches += 1         
            copy_to_c = False
            copy_to_l = False
            copy_to_m = True
            moves[matches] = ''
        elif char == 'c':
            matches += 1         
            copy_to_c = True
            copy_to_m = False
            copy_to_l = False
            curves[matches] = ''
            curves_relative[matches] = True
        elif char == 'C':
            matches += 1         
            copy_to_c = True
            copy_to_m = False
            copy_to_l = False
            curves[matches] = ''
        elif char == 'l':
            matches += 1         
            copy_to_c = False
            copy_to_m = False
            copy_to_l = True
            lines[matches] = ''
            lines_relative[matches] = True
        if char == 'L':
            matches += 1         
            copy_to_c = False
            copy_to_m = False
            copy_to_l = True
            lines[matches] = ''
        #move to/line to 
        elif(copy_to_m):
           if(',' in char):
               if(len(moves[matches]) > 1 and moves[matches][-1] == ','):
                   continue
               commas_detected += 1
           if(len(c) > i + 2):
               if(commas_detected == 3 and c[i+1] == 'c' or c[i+1] == 'C' or c[i+1] == 'm'or c[i+1] == 'M'or c[i+1] == 'l'or c[i+1] == 'L'):
                   commas_detected = 0
                   continue
           if(commas_detected == 3):
               matches += 1
               if(moves_relative.has_key(matches-1)):
                   moves_relative[matches] = True
               moves[matches] = ''
               commas_detected = 1
           moves[matches] = moves[matches] + char
        #curves
        elif(copy_to_c):
           if(',' in char):
               if(len(curves[matches]) > 1 and curves[matches][-1] == ','):
                   continue
               commas_detected += 1
           if(len(c) > i + 2):
               if(commas_detected == 3 and c[i+1] == 'c' or c[i+1] == 'C' or c[i+1] == 'm'or c[i+1] == 'M'or c[i+1] == 'l'or c[i+1] == 'L'):
                   commas_detected = 0
                   continue
           if(commas_detected == 7):
               matches += 1
               if(curves_relative.has_key(matches-1)):
                   curves_relative[matches] = True
               curves[matches] = ''
               commas_detected = 1
           curves[matches] = curves[matches] + char
        #line to 
        elif(copy_to_l):
           if(',' in char):
               if(len(lines[matches]) > 1 and lines[matches][-1] == ','):
                   continue
               commas_detected += 1
           if(len(c) > i + 2):
               if(commas_detected == 3 and c[i+1] == 'c' or c[i+1] == 'C' or c[i+1] == 'm'or c[i+1] == 'M'or c[i+1] == 'l'or c[i+1] == 'L'):
                   commas_detected = 0
                   continue
           if(commas_detected == 3):
               matches += 1
               if(lines_relative.has_key(matches-1)):
                   lines_relative[matches] = True
               lines[matches] = ''
               commas_detected = 1
           lines[matches] = lines[matches] + char

    for i, x in lines.iteritems():
        if(lines_relative.has_key(i) and i != 0):
            val = get_prev_value(i, curves, lines, moves)
            if(val != ''):
                values = get_translated_values(x[1:],0, 0, 0, 0,val[-2],val[-1])
            else:
                values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        else:
            values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        lines[i] = values[0]
        #lines[i] = get_translated_values(x[1:-1],x_trans, y_trans, x_untrans[i], y_untrans[i])[0]

    for i, x in moves.iteritems():
        if(moves_relative.has_key(i) and i != 0):
            val = get_prev_value(i, curves, lines, moves)
            if(val != ''):
                values = get_translated_values(x[1:],0, 0, 0, 0,val[-2],val[-1])
            else:
                values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        else:
            values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        moves[i] = values[0]
        #moves[i] = get_translated_values(x[1:-1],x_trans, y_trans, x_untrans[i], y_untrans[i])[0]

    curve_types = []
    for i, x in curves.iteritems():
        if(curves_relative.has_key(i) and i != 0):
            print curves_relative 
            val = get_prev_value(i, curves, lines, moves)
            if(val != ''):
                values = get_translated_values(x[1:],0, 0, 0, 0,val[-2],val[-1])
            else:
                values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        else:
            values = get_translated_values(x[1:],x_trans, y_trans, x_untrans[k], y_untrans[k])
        curves[i] = values[0]
        m2 = values[1]
        curve_mag = len(m2) / 2
        if(curve_mag == 2):
            curve_types.append('{PathIterator.SEG_QUADTO, new Double[]{')
        elif(curve_mag == 3):
            curve_types.append('{PathIterator.SEG_CUBICTO, new Double[]{')
        else:
            curve_types.append("{PathIterator.SEG_CURVETO, new Double[]{")

    #build output
    i = 0
    output = ''
    while(True):
       if(curves.has_key(i)):
           output += (curve_types[curves_itr]) + curves[i] + '}},'
           curves_itr += 1
       elif(moves.has_key(i)):
           output += '{PathIterator.SEG_MOVETO, new Double[]{' + (moves[i]) + '}},'
       elif(lines.has_key(i)):
           output += '{PathIterator.SEG_LINETO, new Double[]{' + (lines[i]) + '}},'
       else:
           break
       i += 1
    print output[:-1]
    curves_itr = 0
    k += 1
